const express = require('express')

let formidable = require('formidable')
let path = require('path')
let fs = require('fs')

const port = 3000
const app = express()

// 上传图片
app.post('/img/upload', (req, res) => {
    let datas = {}
    datas.code = '0'
    datas.message = '上传图片成功'
    var form = new formidable.IncomingForm()
    form.encoding = 'utf-8'
    // 获取上一级目录的路径
    let filedr = '/file/image'
    form.uploadDir = path.join(path.resolve('../') + filedr)
    
    form.keepExtensions = true; // 保留后缀
    form.maxFieldSize = 10 * 1024 * 1024

    // 处理图片
    form.parse(req, function(err, field, files) {
        console.log('这里没问题')
        console.log(files.file)
        let filename = files.file.name
        let nameArray = filename.split('.')
        let type = nameArray[nameArray.length - 1]
        let name = ''
        for(let i = 0; i < nameArray.length - 1; i++) {
            name = name + nameArray[i]
        }
        let date = new Date()
        let avatarName = '/' + date.getTime() + '.' + type
        let newPath = form.uploadDir + avatarName
        fs.renameSync(files.file.path, newPath)
        let data = {}
        data.name = avatarName
        data.imageUrl = '/api/img/download/' + avatarName
        datas.data = data
        res.send(datas)
        return 
    })
})

// 加载图片
app.get('/img/download/*', (req, res) => {
    req.url = req.url.replace('/img/download', '')
    console.log('收到请求', req.url)
    let filedr = '/file/image'
    let file_path = path.resolve('../') + filedr + req.url;

    fs.readFile(file_path, 'binary', (err, data) => {
        if(err) {
            console.log(err)
        } else {
            console.log('输出文件')
            // 不加这句,页面可能会乱码,图片包含中文也会乱码
            res.writeHead(200, {
                'Content-Type': 'image/jpeg'
            })
            res.write(data, 'binary')
            res.end()
        }
    })
})

app.post('/test', (req, res) => {
    res.send({
        x: 1
    })
})

// app.post('/t/test', (req, res) => {
//     res.send({
//         x: 1
//     })
// })

app.listen(port, () => {
    console.log(`api服务启动成功,端口监听:${port}`)
})

// "test": "echo \"Error: no test specified\" && exit 1"