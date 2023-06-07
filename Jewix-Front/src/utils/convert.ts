export function timeStampToTimes(timeStamp: number) {
    const date = new Date(timeStamp)
    const Y = date.getFullYear()
    const M = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1
    const D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate()
    const h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours()
    const m = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()
    const s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds()
    return [Y.toString(), M.toString(), D.toString(), h.toString(), m.toString(), s.toString()]
}
export function timesToTime(times: string[]) {
    const units = ["年", "月", "日", "时", "分", "秒"]

    return times.map((time, index) => time + units[index]).join("")
}
/**
 * 返回以target的键集作为返回对象的键集，source的键值或者target的默认键值作为返回对象的键值
 * @param source 源数据
 * @param target 目标数据
 * @param map target键映射为source的键的映射表、或target某一键对应的函数
 */
export function convert<T, S>(source: S, target: T, map: { [key in keyof T]?: string | ((source: S) => T[key]) }) {
    const data = { ...target }

    for (const key in data) {
        // 函数则执行， 传入源数据中的属性值
        if (map[key] instanceof Function) {
            // @ts-ignore
            data[key] = map[key](source)
        } else {
            // 否则直接赋值
            // @ts-ignore
            data[key] = map[key] ? source[map[key]] : source[key]
            // 若key不在source属性中（即后端没返回对应的前端所需要的字段）
            // @ts-ignore
            data[key] = data[key] ?? target[key]
        }
    }

    return data
}
type KeyMap<SourceObject> = { [key in keyof SourceObject]?: unknown | ((source: SourceObject) => SourceObject[key]) }
type ChildrenKeyName<SourceObject, KeyMapObject> = Omit<keyof SourceObject, keyof KeyMapObject> | KeyMapObject[keyof KeyMapObject]
/**
 * 将对象的键转换为另一种键
 * @param source  要转换的对象
 * @param keyMap 键映射表
 * @returns 转换后的对象
 */
export function convertKey<T, KM extends KeyMap<T>>(source: T, keyMap: KM) {
    // @ts-ignore
    const data = {} as Omit<T, keyof KM> & { [key in keyof KM as KM[key]]: T[key] }

    for (const key in source) {
        const value = source[key]
        if (key in keyMap) {
            // @ts-ignore
            data[keyMap[key]] = value
        } else {
            // @ts-ignore
            data[key] = value
        }
    }

    return data
}
convertKey.getTool = <KM extends Record<symbol | number | string, unknown>>(keyMap: KM) => {
    return <S>(source: S) => convertKey(source, keyMap)
}
export function convertKeyOfArray<T, KM extends KeyMap<T>>(source: T[], keyMap: KM) {
    return source.map(item => convertKey(item, keyMap))
}
export function convertKeyOfTree<T, KM extends KeyMap<T>, CN extends ChildrenKeyName<T, KM>>(source: T, keyMap: KM, childrenKeyName: CN) {
    const converted = convertKey(source, keyMap)

    // @ts-ignore
    const children = converted[childrenKeyName]

    if (children instanceof Array) {
        // @ts-ignore
        converted[childrenKeyName] = convertKeyOfArray(children, keyMap)
    } else if (typeof children === "object") {
        // @ts-ignore
        converted[childrenKeyName] = convertKeyOfTree(children, keyMap, childrenKeyName)
    }
    // @ts-ignore
    type Result = Omit<typeof converted, CN> & { [key in CN]: Result[] }

    return converted as unknown as Result
}
export function convertKeyOfDeepArray<T, KM extends KeyMap<T>, CN extends ChildrenKeyName<T, KM>>(source: T[], keyMap: KM, childrenKeyName: CN) {
    return source.map(item => convertKeyOfTree(item, keyMap, childrenKeyName))
}
