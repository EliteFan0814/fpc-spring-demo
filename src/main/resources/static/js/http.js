// 封装请求
const http = function (url, method = "GET", body) {
    return new Promise(function (resolve, reject) {
        fetch(url, {
            method, headers: {
                "Content-Type": "application/json"
            }, body: JSON.stringify(body)
        }).then(async response => {
            const {status, headers} = response
            const contentType = headers.get("Content-Type");
            if (status === 200) {
                let body = undefined;
                if (contentType.includes("application/json")) {
                    body = await response.json();
                } else if (contentType.includes("text/html")) {
                    body = await response.text();
                }
                resolve(body);
            } else {
                reject(false);
            }
        }).catch(error => {
            console.log(error);
        })
    })
}
const watchClick = function (target, url, method = "GET", body) {
    let targetDom = document.getElementById(target);
    return targetDom.addEventListener("click", function (e) {
        http(url, method, body).then(res => {
            console.log(res)
        })
    })
}
