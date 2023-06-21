import { HTMLAttributes, ReactElement } from "react";
type SafariProps = HTMLAttributes<HTMLDivElement>;
export default function Safari({ className, ...rest }: SafariProps): ReactElement {
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
                    d="M512 1024a512 512 0 1 1 512-512 512 512 0 0 1-512 512z m0-950.857143a438.857143 438.857143 0 1 0 438.857143 438.857143A438.857143 438.857143 0 0 0 512 73.142857z m95.597714 468.370286a107.300571 107.300571 0 0 1-90.002285 76.324571l-197.485715 208.457143 76.324572-291.547428a98.742857 98.742857 0 0 1 18.285714-89.636572L402.285714 438.857143l301.714286-277.686857L621.714286 548.571429z m-124.342857-62.171429a35.693714 35.693714 0 0 0-11.885714 11.995429 36.571429 36.571429 0 1 0 63.341714 36.571428 35.620571 35.620571 0 0 0 4.132572-20.809142z"
                    fill="#F9FCFA"
                ></path>
            </svg>
        </i>
    );
}
