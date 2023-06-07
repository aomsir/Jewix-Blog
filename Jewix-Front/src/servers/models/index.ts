export interface BaseEntity {
    createTime: string
    updateTime: string
    status: number
}

export const createBaseModel = (base?: Partial<BaseEntity>) => ({
    createTime: "",
    updateTime: "",
    status: 0,
    ...base,
})
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
