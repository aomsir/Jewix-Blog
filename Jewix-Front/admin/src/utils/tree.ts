export function treeReduce<T, R>(
    tree: T[],
    callbackfn: (value: R, node: T) => R,
    initialValue: R,
    configs: {
        props: {
            children: keyof T;
        };
    },
) {
    const queue = tree;
    let value = initialValue;
    while (queue.length) {
        const node = queue.shift();
        if (node) {
            // 业务代码
            value = callbackfn(value, node);
            // 循环代码
            queue.push(...((node[configs?.props.children ?? "children"] as any) ?? []));
        }
    }

    return value;
}
