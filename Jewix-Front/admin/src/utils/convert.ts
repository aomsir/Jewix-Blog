/**
 * 时间戳转换成本地时间
 * @param {number} timestamp 时间戳
 * @returns
 */
export function timestampToTime(timestamp: number) {
  if (!timestamp) return;
  if (timestamp.toString().length === 10) {
    //时间戳为10位需*1000，时间戳为13位的话不需乘1000
    timestamp = timestamp * 1000;
  }
  const date = new Date(timestamp);
  const Y = date.getFullYear() + '-';
  const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
  const D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
  const h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
  const m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
  const s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();

  return Y + M + D + h + m + s;
}
