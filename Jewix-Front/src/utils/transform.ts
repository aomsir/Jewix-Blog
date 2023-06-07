/**
 * 给对象添加新的属性
 * @param source 需要被添加属性的对象
 * @param delta 增量函数，返回一个对象，这个对象的键值会被添加到source中
 * @returns 添加了新属性的对象
 */
export function addKey<S, DeltaObject>(source: S, delta: (source: S) => DeltaObject) {
    return {
        ...source,
        ...delta(source),
    } as Omit<S, keyof DeltaObject> & DeltaObject
}
export function addKeyOfArray<S, DeltaObject>(source: S[], delta: (source: S) => DeltaObject) {
    return source.map(item => addKey(item, delta))
}
export function addKeyOfTree<S, DeltaObject, CN extends keyof S | keyof DeltaObject>(source: S, delta: (source: S) => DeltaObject, childrenName: CN) {
    const added = addKey(source, delta)

    const children = added[childrenName as keyof typeof added]

    if (children instanceof Array) {
        // @ts-ignore
        added[childrenName] = addKeyOfArray(children, delta)
    } else if (typeof children === "object") {
        // @ts-ignore
        added[childrenName] = addKeyOfTree(children, delta, childrenName)
    }

    type Result = Omit<typeof added, CN> & { [key in CN]: Result[] }

    return added as unknown as Result
}
/**
 * 对对象新增属性、转换键名
 * @param source 要转换的对象
 * @param tools 工具函数
 */
// export function transform<S extends Record<any, unknown>>(source: S, tools: { convertKeyTool?: ReturnType<typeof convertKey.getTool> /*  addKey?: typeof addKey  */ }) {
//     let data = source

//     if (tools.convertKeyTool) {
//         data = tools.convertKeyTool(data)
//     }

//     /* if (tools.addKey) {
//         data = tools.addKey(data)
//     } */

//     return data
// }

/* type T1 = { name: string }
type T2 = { name: number } */
type T3 = { name: string } & { name: number }
