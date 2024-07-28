export interface Result<T> {
  readonly code: number
  readonly data?: T | undefined
  readonly msg: string
}
