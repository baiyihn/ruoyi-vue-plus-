module.exports = {
    transpileDependencies: ["vuetify"],
    devServer: {
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
                logLevel: 'debug',
                pathRewrite: {
                    "^/api": ""
                }
            }
        },
        disableHostCheck: true
    },
    productionSourceMap: false,
    css: {
        extract: true,
        sourceMap: false
    }
};
