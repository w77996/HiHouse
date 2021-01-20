module.exports = {
  lintOnSave: false,
  devServer: {
    overlay: {
      warnings: true,
      error: true
    },
    proxy: {
      '/api/*': {
        // target: 'http://121.40.117.185:8181/',
        target: 'http://localhost:8185/',
        changeOrigin: true,
        ws: true,
        xfwd: false,
        pathRewrite: {
          '^/api': '/'
        }
      }
    }
  }
}
