import { type ClassValue, clsx } from 'clsx'
import { twMerge } from 'tailwind-merge'
import { calc } from 'a-calc'
import dayjs from 'dayjs'
export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

/**
 * 计算硬盘、磁盘等的使用率
 * @param {string} total 总大小(字节)
 * @param {string} used 已使用大小(字节)
 * @returns {number} 使用率
 */
export function calcUsage(total: string, used: string): number {
  return calc('used / total * 100 | =0!n', { used, total })
}

/**
 * 字节转换
 * @param {string} bytes 字节
 */
export function formatBytes(bytes: string): string {
  if (bytes === '0') return '0 Bytes'
  const k = 1024
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
  const i = Math.floor(Math.log(parseInt(bytes)) / Math.log(k))
  return calc('bytes / k ** i | =0', { bytes, k, i }) + ' ' + sizes[i]
}

/**
 * 计算服务器启动时间
 * @param {number} bootTime 服务器启动时间戳(秒)
 * @returns {string} 服务器开机时长(天|时|分|秒)
 */
export function calcServerTime(bootTime: number): string {
  const now = dayjs().unix()
  const diff = calc('now - bootTime', { now, bootTime })
  const days = calc('diff / 86400 | =0%!n', { diff })
  const hours = calc('diff % 86400 / 3600 | =0%!n', { diff })
  const minutes = calc('(diff % 3600) / 60 | =0%!n', { diff })
  if (days > 0) {
    return `${days} 天 ${hours} 时 ${minutes} 分`
  } else if (hours > 0) {
    return `${hours} 时 ${minutes} 分`
  } else {
    return `${minutes} 分`
  }
}
