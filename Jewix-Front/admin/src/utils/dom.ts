// 复制到粘贴板
export function copyToClipboard(text: string) {
  const inputEl = document.createElement('input');
  inputEl.value = text;
  document.body.appendChild(inputEl);
  inputEl.select();
  document.execCommand('copy');
  document.body.removeChild(inputEl);
}
