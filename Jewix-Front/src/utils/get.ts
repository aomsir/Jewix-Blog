// 从树形结构中获取某个树
export function findFromTree<T extends Record<any, unknown>>(obj: T, predicate: (node: T) => boolean, childrenKeyName: keyof T) {
    const children = obj[childrenKeyName] as unknown as T[]
    
    // 如果本身就是查找节点
    if (predicate(obj)) {
        return obj
    }
    if (children) {
        // 查找子节点
        for (const child of children) {
            const target = findFromTree(child, predicate, childrenKeyName) as T

            if (target) {
                return target
            }
        }
    }
}
