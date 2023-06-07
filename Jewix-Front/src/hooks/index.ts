import { Dispatch, FormEventHandler, SetStateAction, useState } from "react"

export function useInput<T>(initialValue: T) {
    const [value, setValue] = useState(initialValue)

    return [
        [value, setValue],
        {
            value,
            onChange(e: any) {
                setValue(e.target.value)
            },
        },
    ] as const
}
