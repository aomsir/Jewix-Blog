import { createContext, useCallback, useContext, useMemo, useState, type PropsWithChildren } from "react";
import { ConfigProvider } from "antd";
import antdEnUS from "antd/locale/en_US";
import antdZhCN from "antd/locale/zh_CN";
import { IntlProvider } from "react-intl";
import enUSMessages from "@/locales/en-US";
import zhCNMessages from "@/locales/zh-CN";

export const supportedLocales = ["zh-CN", "en-US"] as const;
export type AppLocale = (typeof supportedLocales)[number];

const localeMessages: Record<AppLocale, Record<string, string>> = {
  "zh-CN": zhCNMessages,
  "en-US": enUSMessages,
};

const antdLocales = {
  "zh-CN": antdZhCN,
  "en-US": antdEnUS,
} as const;

type LocaleState = {
  locale: AppLocale;
  setLocale: (locale: AppLocale) => void;
};

const LocaleContext = createContext<LocaleState | undefined>(undefined);

export function useLocale() {
  const value = useContext(LocaleContext);
  if (!value) throw new Error("useLocale must be used inside LocaleProvider");
  return value;
}

function isAppLocale(value: string): value is AppLocale {
  return value === "zh-CN" || value === "en-US";
}

export function LocaleProvider({ children }: PropsWithChildren) {
  const [locale, setLocaleState] = useState<AppLocale>("zh-CN");
  const setLocale = useCallback((next: AppLocale) => {
    if (isAppLocale(next) && localeMessages[next]) {
      setLocaleState(next);
    }
  }, []);
  const value = useMemo(() => ({ locale, setLocale }), [locale, setLocale]);
  return <LocaleContext.Provider value={value}>{children}</LocaleContext.Provider>;
}

export function LocaleIntlProvider({ children }: PropsWithChildren) {
  const { locale } = useLocale();
  return (
    <IntlProvider locale={locale} defaultLocale="zh-CN" messages={localeMessages[locale]}>
      <ConfigProvider locale={antdLocales[locale]}>{children}</ConfigProvider>
    </IntlProvider>
  );
}
