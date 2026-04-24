# 单位转换 (convert-units)

[![下载量](https://img.shields.io/npm/dm/convert-units.svg)](https://www.npmjs.com/package/convert-units)
[![版本](https://img.shields.io/npm/v/convert-units.svg)](https://www.npmjs.com/package/convert-units)

一个用于在不同单位之间进行数量转换的便捷工具。支持多种测量类型，包括长度、面积、体积、质量、温度等。

## 特性

- ✨ 简单易用的链式API
- 📦 支持多种测量类型（长度、面积、体积、质量、温度、时间等）
- 🔧 可自定义测量单位
- 💪 类型安全（TypeScript支持）
- 🚀 支持ES6模块和CommonJS
- ⚡ 高性能转换算法
- 🌐 支持国际单位制(SI)与英制单位互转

## 安装

```bash
npm install convert-units --save
```

```bash
# beta 版本也可以通过以下命令安装
npm install convert-units@beta --save
```

## 快速开始

`convert-units` 具有简单易读的链式API。它也可以使用随库打包的测量单位或自定义测量单位进行配置。

下面的代码片段显示了开始所需的一切：

```js
// `allMeasures` 包含此库中包含的所有测量单位
import configureMeasurements from 'convert-units';
import allMeasures from 'convert-units/definitions/all';

const convert = configureMeasurements(allMeasures);

// 示例：转换 1 升到毫升
convert(1).from('l').to('ml'); // 返回 1000
```

也可以限制配置的测量单位。这允许在使用像 `webpack` 或 `rollup` 这样的打包器时创建更小的包：

```js
import configureMeasurements from 'convert-units';
import volume from 'convert-units/definitions/volume';
import length from 'convert-units/definitions/length';
import mass from 'convert-units/definitions/mass';

/*
  `configureMeasurements` 是一个接受目录的闭包
  的测量单位并返回一个工厂函数（`convert`），该函数使用
  只有这些测量单位。
*/
const convert = configureMeasurements({
    volume,
    mass,
    length,
});
```

## 基本用法

### 转换单位

```js
// 同一测量单位内的转换
convert(1).from('l').to('ml');           // 1000
convert(1).from('km').to('m');           // 1000
convert(1).from('kg').to('g');           // 1000
convert(1).from('lb').to('kg');          // 0.4536... (自动单位制转换)
```

### 系统间自动转换

```js
// 自动处理系统间的转换（例如英制到公制）
convert(1).from('lb').to('kg');          // 0.4536... (英制到公制)
convert(1).from('in').to('cm');          // 2.54 (英制到公制)
```

### 错误处理

尝试在不同测量单位间转换会导致错误：

```js
convert(1).from('oz').to('fl-oz');
// 抛出错误 -- 不能从质量转换到体积！
```

### 最佳单位转换

将单位转换为同一测量单位中最小值大于 `1` 的另一个单位：

```js
convert(12000).from('mm').toBest();
// { val: 12, unit: 'm', singular: 'Meter', plural: 'Meters' }

// 如果没有找到更好的值，则返回原单位
convert(1).from('mm').toBest();
// { val: 1, unit: 'mm', singular: 'Millimeter', plural: 'Millimeters' }

// 排除某些单位以获得不同的结果
convert(12000).from('mm').toBest({ exclude: ['m'] });
// { val: 1200, unit: 'cm', ... } (除米以外的最小单位)

// 自定义截止数值
convert(900).from('mm').toBest({ cutOffNumber: 10 });
// { val: 90, unit: 'cm', ... } (值等于或大于10的最小单位)

// 强制指定目标系统
convert(254).from('mm').toBest({ system: 'imperial' });
// { val: 10, unit: 'in', plural: 'Inches' } (强制使用英制)
```

## 高级功能

### 列出可用的测量单位

```js
// 列出所有可用的测量类型
convert().measures();
// [ 'length', 'mass', 'volume', 'area', 'temperature', ... ]

// 创建特定测量单位的转换器
const differentConvert = configureMeasurements({
    volume,
    mass,
    length,
    area,
});
differentConvert().measures();
// [ 'length', 'mass', 'volume', 'area' ]
```

### 查找可转换的单位

```js
// 列出可以从某单位转换到的所有单位
convert().from('l').possibilities();
// [ 'ml', 'l', 'tsp', 'Tbs', 'fl-oz', 'cup', 'pnt', 'qt', 'gal' ]

convert().from('kg').possibilities();
// [ 'mcg', 'mg', 'g', 'kg', 'oz', 'lb' ]

// 列出属于某个测量类型的全部单位
convert().possibilities('mass');
// [ 'mcg', 'mg', 'g', 'kg', 'oz', 'lb', 'mt', 't' ]

// 列出所有已配置的单位
convert().possibilities();
// [ 'mm', 'cm', 'm', 'in', 'ft-us', 'ft', 'mi', 'mcg', 'mg', 'g', 'kg', 'oz', 'lb', 'mt', 't', 'ml', 'l', 'tsp', 'Tbs', 'fl-oz', 'cup', 'pnt', 'qt', 'gal', 'ea', 'dz' ];
```

### 获取单位详情

```js
// 获取单位的详细描述
convert().describe('kg');
/*
  {
    abbr: 'kg',
    measure: 'mass',
    system: 'metric',
    singular: 'Kilogram',
    plural: 'Kilograms',
  }
*/

// 列出所有单位的详细信息
convert().list();
/*
  [{
    abbr: 'kg',
    measure: 'mass',
    system: 'metric',
    singular: 'Kilogram',
    plural: 'Kilograms',
  }, ...]
*/

// 列出特定测量类型的所有单位详情
convert().list('mass');
/*
  [{
    abbr: 'kg',
    measure: 'mass',
    system: 'metric',
    singular: 'Kilogram',
    plural: 'Kilograms',
  }, ...]
*/
```

## 自定义测量单位

要创建自定义测量单位，最好从一个普通对象开始。键本身将用作测量单位的名称。在下面的示例中，测量单位的名称是"`customMeasure`"。

<details>
<summary>基础结构示例</summary>

```js
const measure = {
  customMeasure: {},
};
```
</details>

下一步是创建测量单位的系统。系统是一组相关的单位。这里有一些常见系统的示例：公制、英制、国际单位制、比特、字节等。您不需要为您的测量单位使用这些系统之一。

<details>
<summary>系统定义示例</summary>

```js
const measure = {
  customMeasure: {
    systems: {
      A: {},
      B: {},
      C: {},
    }
  },
};
```
</details>

现在测量单位已准备好定义一些单位。需要为每个系统定义的第一个单位是基本单位。基本单位与所有其他单位类似，只是它是用于在系统之间转换的单位，并且系统中的每个其他单位都将被配置为直接转换为它。

<details>
<summary>基本单位定义示例</summary>

```js
const measure = {
  customMeasure: {
    systems: {
      A: {
        a: {  // 单位的名称（通常是缩写）
          name: {  // 单位的人性化名称
            singular: 'a',
            plural: 'as',
          },
          to_anchor: 1,  // 基本单位的锚点值必须为1
        }
      },
      // 暂时忽略 C & B
    }
  },
};
```
</details>

每个单位还需要一个 `to_anchor` 属性。`to_anchor` 包含一个数字，表示从系统中另一个单位转换到基本单位所需的因子。

<details>
<summary>单位转换因子示例</summary>

```js
const measure = {
  customMeasure: {
    systems: {
      A: {
        ah: {  // 大单位
          name: {
            singular: 'ah',
            plural: 'ahs',
          },
          to_anchor: 1e1,  // = 10 ^ 1 = 10 (比基本单位大10倍)
        },
        a: {
          name: {
            singular: 'a',
            plural: 'as',
          },
          to_anchor: 1,  // 基本单位
        },
        al: {  // 小单位
          name: {
            singular: 'al',
            plural: 'als',
          },
          to_anchor: 1e-1,  // = 10 ^ -1 = 0.1 (比基本单位小10倍)
        },
      },
    }
  },
};
```
</details>

还可以使用 `anchor_shift` 属性定义偏移量（主要用于温度转换）：

<details>
<summary>带偏移量的单位定义示例</summary>

```js
const measure = {
  customMeasure: {
    systems: {
      A: {
        ah: {
          name: {
            singular: 'ah',
            plural: 'ahs',
          },
          to_anchor: 1e1,
        },
        a: {
          name: {
            singular: 'a',
            plural: 'as',
          },
          to_anchor: 1,
        },
        al: {
          name: {
            singular: 'al',
            plural: 'als',
          },
          to_anchor: 1e-1,
          anchor_shift: 5,  // 转换后偏移5个单位
        },
      },
    },
    // 系统间转换定义
    anchors: {
      A: {
        B: {
          ratio: 2,  // A系统到B系统的比率
        },
        C: {
          ratio: 3,  // A系统到C系统的比率
        },
      },
      B: {
        A: {
          ratio: 1 / 2,  // B系统到A系统的比率
        },
        C: {
          transform: value => value * 1.5,  // 使用转换函数
        },
      },
      C: {
        A: {
          ratio: 1 / 3,
        },
        B: {
          ratio: 2 / 3,
        },
      },
    },
  }
};

const convert = configureMeasurements(measure);
convert(1).from('a').to('bl');  // 示例转换
```
</details>

## 扩展现有测量单位

由于测量单位定义是普通的JS对象，因此可以添加、删除和修改额外的单位。

<details>
<summary>扩展现有测量单位示例</summary>

```ts
import configureMeasurements, {
  Measure
} from 'convert-units';

import
  length, {
  LengthSystems,
  LengthUnits,
} from "convert-units/definitions/length"

type NewLengthUnits = LengthUnits | 'px';
const DPI = 96;
const extendedLength: Measure<LengthSystems, NewLengthUnits> = {
  systems: {
    metric: {
      ...length.systems.metric,
      px: {
        name: {
          singular: 'Pixel',
          plural: 'Pixels',
        },
        to_anchor: 0.0254 / DPI,
      },
    },
    imperial: {
      ...length.systems.imperial,
    },
  },
  anchors: {
    ...length.anchors,
  },
};

const convert = configureMeasurements<'length', LengthSystems, NewLengthUnits>(
  { length: extendedLength }
);

convert(4).from('cm').to('px');
// 151.18110236220474
```
</details>

## 从v2迁移到v3+

这只适用于从 `<=2.3.4` 移动到 `>=3.x` 的情况。
 
旧版代码：
```js
import convert from 'convert-units';

convert(1).from('m').to('mm');
convert(1).from('m').to('ft');
```

新版代码：

`index.js`
```js
import convert from './convert';  // 在下面定义
```

`convert.js`
```js
import configureMeasurements from 'convert-units';
import allMeasures from 'convert-units/definitions/all';  

export default configureMeasurements(allMeasures);
```

## TypeScript 支持

该库为所有打包的测量单位提供类型：

```ts
import configureMeasurements from 'convert-units';

import length, {
  LengthSystems,
  LengthUnits,
} from "convert-units/definitions/length"

import area, {
  AreaSystems,
  AreaUnits,
} from "convert-units/definitions/area"

// Measures: 正在使用的测量单位的名称
type Measures = 'length' | 'area';
// Systems: 跨所有测量单位使用的系统
type Systems = LengthSystems | AreaSystems;
// Units: 跨所有测量单位及其系统的全部单位
type Units = LengthUnits | AreaUnits;

const convert = configureMeasurements<Measures, Systems, Units>({
  length,
  area,
});

convert(4).from('m').to('cm');
// 400
```

这也允许IDE工具在运行应用程序之前突出显示问题：

```ts
import configureMeasurements from 'convert-units';

import length, {
  LengthSystems,
  LengthUnits,
} from "convert-units/definitions/length"

import area, {
  AreaSystems,
  AreaUnits,
} from "convert-units/definitions/area"

// Measures: 正在使用的测量单位的名称
type Measures = 'length' | 'area';
// Systems: 跨所有测量单位使用的系统
type Systems = LengthSystems | AreaSystems;
// Units: 跨所有测量单位及其系统的全部单位
type Units = LengthUnits | AreaUnits;

const convert = configureMeasurements<Measures, Systems, Units>({
  length,
  area,
});

convert(4).from('wat').to('cm');  // TypeScript 会警告 'wat' 单位不存在
```

`allMeasures` 对象的类型也提供了：

```ts
import configureMeasurements from 'convert-units';

import allMeasures, {
  AllMeasures,
  AllMeasuresSystems,
  AllMeasuresUnits,
} from 'convert-units/definitions/all';

const convertAll = configureMeasurements<
  AllMeasures,
  AllMeasuresSystems,
  AllMeasuresUnits
>(allMeasures);

convertAll(4).from('m2').to('cm2');
// 40000
```

## 支持的单位类型

### 长度
<details>
<summary>点击查看</summary>

* nm (纳米)
* μm (微米)
* mm (毫米)
* cm (厘米)
* m (米)
* km (千米)
* in (英寸)
* yd (码)
* ft-us (美国英尺)
* ft (英尺)
* fathom (英寻)
* mi (英里)
* nMi (海里)
</details>

### 面积
<details>
<summary>点击查看</summary>

* mm2 (平方毫米)
* cm2 (平方厘米)
* m2 (平方米)
* ha (公顷)
* km2 (平方千米)
* in2 (平方英寸)
* ft2 (平方英尺)
* ac (英亩)
* mi2 (平方英里)
</details>

### 质量
<details>
<summary>点击查看</summary>

* mcg (微克)
* mg (毫克)
* g (克)
* kg (千克)
* oz (盎司)
* lb (磅)
* mt (公吨)
* st (英石)
* t (美吨)
</details>

### 体积
<details>
<summary>点击查看</summary>

* mm3 (立方毫米)
* cm3 (立方厘米)
* ml (毫升)
* l (升)
* kl (千升)
* Ml (兆升)
* Gl (吉升)
* m3 (立方米)
* km3 (立方千米)
* tsp (茶匙)
* Tbs (汤匙)
* in3 (立方英寸)
* fl-oz (液盎司)
* cup (杯)
* pnt (品脱)
* qt (夸脱)
* gal (加仑)
* ft3 (立方英尺)
* yd3 (立方码)
</details>

### 体积流量
<details>
<summary>点击查看</summary>

* mm3/s
* cm3/s
* ml/s
* cl/s
* dl/s
* l/s
* l/min
* l/h
* kl/s
* kl/min
* kl/h
* m3/s
* m3/min
* m3/h
* km3/s
* tsp/s
* Tbs/s
* in3/s
* in3/min
* in3/h
* fl-oz/s
* fl-oz/min
* fl-oz/h
* cup/s
* pnt/s
* pnt/min
* pnt/h
* qt/s
* gal/s
* gal/min
* gal/h
* ft3/s
* ft3/min
* ft3/h
* yd3/s
* yd3/min
* yd3/h
</details>

### 温度
<details>
<summary>点击查看</summary>

* C (摄氏度)
* F (华氏度)
* K (开尔文)
* R (兰氏度)
</details>

### 时间
<details>
<summary>点击查看</summary>

* ns (纳秒)
* mu (微秒)
* ms (毫秒)
* s (秒)
* min (分钟)
* h (小时)
* d (天)
* week (周)
* month (月)
* year (年)
</details>

### 频率
<details>
<summary>点击查看</summary>

* Hz (赫兹)
* mHz (毫赫兹)
* kHz (千赫兹)
* MHz (兆赫兹)
* GHz (吉赫兹)
* THz (太赫兹)
* rpm (转/分钟)
* deg/s (度/秒)
* rad/s (弧度/秒)
</details>

### 速度
<details>
<summary>点击查看</summary>

* m/s (米/秒)
* km/h (千米/小时)
* mph (英里/小时)
* knot (节)
* ft/s (英尺/秒)
* in/h (英寸/小时)
* mm/h (毫米/小时)
</details>

### 扭矩
<details>
<summary>点击查看</summary>

* Nm (牛顿米)
* lbf-ft (磅力英尺)
</details>

### 步速
<details>
<summary>点击查看</summary>

* s/m (秒/米)
* min/km (分钟/千米)
* s/ft (秒/英尺)
* min/mi (分钟/英里)
</details>

### 压力
<details>
<summary>点击查看</summary>

* Pa (帕斯卡)
* hPa (百帕)
* kPa (千帕)
* MPa (兆帕)
* bar (巴)
* torr (托)
* mH2O (米水柱)
* mmHg (毫米汞柱)
* psi (磅/平方英寸)
* ksi (千磅/平方英寸)
</details>

### 数字存储
<details>
<summary>点击查看</summary>

* bit (比特)
* byte (字节)
* kb (千比特)
* Mb (兆比特)
* Gb (吉比特)
* Tb (太比特)
* kB (千字节)
* MB (兆字节)
* GB (吉字节)
* TB (太字节)
* KiB (二进制千字节)
* MiB (二进制兆字节)
* GiB (二进制吉字节)
* TiB (二进制太字节)
</details>

### 照度
<details>
<summary>点击查看</summary>

* lx (勒克斯)
* ft-cd (英尺烛光)
</details>

### 浓度
<details>
<summary>点击查看</summary>

* ppm (百万分率)
* ppb (十亿分率)
* ppt (万亿分率)
* ppq (千万亿分率)
</details>

### 电压
<details>
<summary>点击查看</summary>

* V (伏特)
* mV (毫伏)
* kV (千伏)
</details>

### 电流
<details>
<summary>点击查看</summary>

* A (安培)
* mA (毫安)
* kA (千安)
</details>

### 功率
<details>
<summary>点击查看</summary>

* W (瓦特)
* mW (毫瓦)
* kW (千瓦)
* MW (兆瓦)
* GW (吉瓦)
* PS (马力)
* Btu/s (英热单位/秒)
* ft-lb/s (英尺磅/秒)
* hp (马力)
</details>

### 视在功率
<details>
<summary>点击查看</summary>

* VA (伏安)
* mVA (毫伏安)
* kVA (千伏安)
* MVA (兆伏安)
* GVA (吉伏安)
</details>

### 无功功率
<details>
<summary>点击查看</summary>

* VAR (乏)
* mVAR (毫乏)
* kVAR (千乏)
* MVAR (兆乏)
* GVAR (吉乏)
</details>

### 能量
<details>
<summary>点击查看</summary>

* Ws (瓦秒)
* Wm (瓦分)
* Wh (瓦时)
* mWh (毫瓦时)
* kWh (千瓦时)
* MWh (兆瓦时)
* GWh (吉瓦时)
* J (焦耳)
* kJ (千焦)
* MJ (兆焦)
* GJ (吉焦)
</details>

### 无功能量
<details>
<summary>点击查看</summary>

* VARh (乏时)
* mVARh (毫乏时)
* kVARh (千乏时)
* MVARh (兆乏时)
* GVARh (吉乏时)
</details>

### 角度
<details>
<summary>点击查看</summary>

* deg (度)
* rad (弧度)
* grad (梯度)
* arcmin (弧分)
* arcsec (弧秒)
</details>

### 电荷
<details>
<summary>点击查看</summary>

* c (库仑)
* mC (毫库仑)
* μC (微库仑)
* nC (纳库仑)
* pC (皮库仑)
</details>

### 力
<details>
<summary>点击查看</summary>

* N (牛顿)
* kN (千牛顿)
* lbf (磅力)
* kgf (千克力)
</details>

### 加速度
<details>
<summary>点击查看</summary>

* g (重力加速度)
* m/s2 (米/秒²)
* g0 (标准重力)
</details>

### 件数
<details>
<summary>点击查看</summary>

* pcs (件)
* bk-doz (打)
* cp (份)
* doz-doz ( dozen dozen )
* doz (打)
* gr-gr (gross gross)
* gros (大打)
* half-dozen (半打)
* long-hundred (长百)
* ream (令)
* scores (二十)
* sm-gr (small gross)
* trio (三件套)
</details>

## 贡献

欢迎提交Pull Request和报告Issue！所有新的测量单位和额外的单位都受到欢迎！请查看 [`src/definitions`](https://github.com/convert-units/convert-units/tree/main/src/definitions) 以查看一些示例。

## 许可证

MIT License

Copyright (c) 2013-2024 Ben Ng 和贡献者，http://benng.me

特此免费授予任何获得此软件副本和相关文档
文件（"软件"）的人不受限制地处理
该软件，包括但不限于使用、复制、修改、合并的权利，
发布、分发、再许可和/或销售软件副本，
并允许获得该软件的人员这样做，但须遵守以下条件：

上述版权声明和此许可声明应包含在所有
软件的副本或实质性部分。

该软件按"原样"提供，不提供任何明示或暗示的保证，
包括但不限于适销性、特定用途适用性和非侵权性的保证。
在任何情况下，作者或版权持有者均不对任何索赔、损害或其他责任负责，
无论是在合同诉讼、侵权诉讼或其他诉讼中，由本软件或使用或
其他交易引起或与之相关的任何方式。