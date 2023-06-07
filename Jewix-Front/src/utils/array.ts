// 获取数组的前半部分
export function halfStart<T extends unknown[]>(arr: T) {
    return arr.slice(0, arr.length / 2) as T
}
