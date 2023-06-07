import MarkdownIt from 'markdown-it';

export const md = new MarkdownIt({
  html: true, // 禁用 HTML 实体编码。输入的 <p>1</p> 就会被渲染为 <p>1</p>，而不是 <p>&lt;p&gt;1&lt;/p&gt;</p>。
});
