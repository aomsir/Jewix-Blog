import { Html, Head, Main, NextScript, DocumentProps } from "next/document"
import webSiteConfig from "~/site.config"

export default function Document(props: DocumentProps) {
    return (
        <Html lang="en" data-theme={props.__NEXT_DATA__.props.theme}>
            <Head>
                <link rel="icon" href={webSiteConfig.favicon} />
            </Head>
            <body>
                <Main />
                <NextScript />
            </body>
        </Html>
    )
}
