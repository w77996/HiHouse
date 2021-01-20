// import axios from '@/lib/axios'

export const config = {
  // 正式环境
  wxAppId: '',
  wxAppSecret: '',
  // 测试环境
  // wxAppId: '',
  // wxAppSecret: '',
  redirectWxDomain:''
}

// export const getAccessToken = async () => {
//   console.log('object')
//   const { data } = await axios.get(`https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${config.wxAppId}&secret=${config.wxAppSecret}`)
//   console.log('data', data)
//   return data
// }

// const getJsapiTicket = async () => {
//   const access_token = getAccessToken()
//   const { data } = await axios.get(`https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=${access_token}&type=jsapi`)
//   console.log('data1', data)
// }
