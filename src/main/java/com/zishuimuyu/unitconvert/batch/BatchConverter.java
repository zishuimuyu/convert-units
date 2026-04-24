package com.zishuimuyu.unitconvert.batch;

import com.zishuimuyu.unitconvert.model.ConvertResult;
import com.zishuimuyu.unitconvert.model.UnitEnum;
import com.zishuimuyu.unitconvert.service.IUnitConversionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BatchConverter {
    
    private final IUnitConversionService service;
    private final ExecutorService executorService;
    private final int maxThreads;
    
    public BatchConverter(IUnitConversionService service) {
        this(service, Runtime.getRuntime().availableProcessors());
    }
    
    public BatchConverter(IUnitConversionService service, int maxThreads) {
        this.service = service;
        this.maxThreads = maxThreads;
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }
    
    public List<ConvertResult<BigDecimal>> convertSequential(List<ConversionRequest> conversions) {
        List<ConvertResult<BigDecimal>> results = new ArrayList<>(conversions.size());
        
        for (ConversionRequest request : conversions) {
            try {
                ConvertResult<BigDecimal> result = service.convert(
                    request.getValue(), 
                    request.getFromUnit(), 
                    request.getToUnit()
                );
                results.add(result);
            } catch (Exception e) {
                results.add(new ConvertResult<>(null, "ERROR", "error", "errors"));
            }
        }
        
        return results;
    }
    
    public List<ConvertResult<BigDecimal>> convertParallel(List<ConversionRequest> conversions) {
        List<Future<ConvertResult<BigDecimal>>> futures = new ArrayList<>(conversions.size());
        
        for (ConversionRequest request : conversions) {
            Future<ConvertResult<BigDecimal>> future = executorService.submit(() -> {
                try {
                    return service.convert(
                        request.getValue(), 
                        request.getFromUnit(), 
                        request.getToUnit()
                    );
                } catch (Exception e) {
                    return new ConvertResult<>(null, "ERROR", "error", "errors");
                }
            });
            futures.add(future);
        }
        
        List<ConvertResult<BigDecimal>> results = new ArrayList<>(conversions.size());
        for (Future<ConvertResult<BigDecimal>> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                results.add(new ConvertResult<>(null, "ERROR", "error", "errors"));
            }
        }
        
        return results;
    }
    
    public List<ConvertResult<BigDecimal>> convertToBest(List<BigDecimal> values, UnitEnum fromUnit) {
        List<ConvertResult<BigDecimal>> results = new ArrayList<>(values.size());
        
        for (BigDecimal value : values) {
            try {
                ConvertResult<BigDecimal> result = service.convertToBest(value, fromUnit);
                results.add(result);
            } catch (Exception e) {
                results.add(new ConvertResult<>(null, "ERROR", "error", "errors"));
            }
        }
        
        return results;
    }
    
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    public static class ConversionRequest {
        private final BigDecimal value;
        private final UnitEnum fromUnit;
        private final UnitEnum toUnit;
        
        public ConversionRequest(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
            this.value = value;
            this.fromUnit = fromUnit;
            this.toUnit = toUnit;
        }
        
        public BigDecimal getValue() {
            return value;
        }
        
        public UnitEnum getFromUnit() {
            return fromUnit;
        }
        
        public UnitEnum getToUnit() {
            return toUnit;
        }
        
        public static ConversionRequest of(BigDecimal value, UnitEnum fromUnit, UnitEnum toUnit) {
            return new ConversionRequest(value, fromUnit, toUnit);
        }
    }
}