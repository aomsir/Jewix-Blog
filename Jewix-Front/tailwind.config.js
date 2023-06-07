/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{tsx,js}"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
  base: {
    // 移除默认的 ::before 和 ::after 样式
    '*, ::before, ::after': {
      boxSizing: 'border-box',
    },
  },

}
