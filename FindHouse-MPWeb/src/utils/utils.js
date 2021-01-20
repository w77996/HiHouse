export function trim (str) {
  return String.prototype.trim.call(str)
}

export function isType (arg, type) {
  return Object.prototype.toString.call(arg) === '[object ' + type + ']'
}

export function isWeixin () {
  return navigator.userAgent.toLowerCase().indexOf('micromessenger') !== -1
}

// 退出公众号
export const closePage = () => {
  setTimeout(function () {
    // 安卓手机
    document.addEventListener(
      'WeixinJSBridgeReady',
      function () {
        // eslint-disable-next-line no-undef
        WeixinJSBridge.call('closeWindow')
      },
      false
    )
    // ios手机
    // eslint-disable-next-line no-undef
    WeixinJSBridge.call('closeWindow')
  }, 100)
}

/**
 * 设置title
 * @param {*} title
 */
export const setTitle = (title) => {
  document.title = title
  if (/ip(hone|od|ad)/i.test(navigator.userAgent)) {
    var i = document.createElement('iframe')
    i.src = '/favicon.ico'
    i.style.display = 'none'
    i.onload = function () {
      setTimeout(function () {
        i.remove()
      }, 9)
    }
    document.body.appendChild(i)
  }
}
