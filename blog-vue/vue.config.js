module.exports = {
    publicPath: './',
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:3000/',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api': ''
                }
            },
            '/web': {
                target: 'http://localhost:80/',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/web': ''
                }
            },
        }
    }
}