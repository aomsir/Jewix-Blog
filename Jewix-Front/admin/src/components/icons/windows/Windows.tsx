import { HTMLAttributes, ReactElement } from "react";
type WindowsProps = HTMLAttributes<HTMLDivElement>;
export default function Windows({ className, ...rest }: WindowsProps): ReactElement {
    return (
        <i
            className={`${className ?? ""}`}
            {...rest}
            style={{
                backgroundColor: "#045db5",
                display: "flex",
                padding: "4px",
                borderRadius: "5px",
            }}
        >
            <svg
                viewBox="0 0 1024 1024"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
            >
                <path
                    d="M523.8 191.4v288.9h382V128.1zM523.8 833.6l382 62.2v-352h-382zM120.1 480.2H443V201.9l-322.9 53.5zM120.1 770.6L443 823.2V543.8H120.1z"
                    fill="#ffffff"
                ></path>
            </svg>
        </i>
    );
}
