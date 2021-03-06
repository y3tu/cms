const common = {}

/**
 * 生成uuid方法
 * @returns {string}
 */
common.createUUID = function () {
    let d = new Date().getTime();
    if (window.performance && typeof window.performance.now === "function") {
        d += performance.now(); //use high-precision timer if available
    }
    let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        let r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
    return uuid;
}

/**
 * 深拷贝
 * @param {*} obj 拷贝对象(object or array)
 * @param {*} cache 缓存数组
 */
common.deepClone = function (obj, cache = []) {
    // typeof [] => 'object'
    // typeof {} => 'object'
    if (obj === null || typeof obj !== 'object') {
        return obj
    }
    // 如果传入的对象与缓存的相等, 则递归结束, 这样防止循环
    /**
     * 类似下面这种
     * let a = {b:1}
     * a.c = a
     * 资料: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Errors/Cyclic_object_value
     */
    const hit = cache.filter(c => c.original === obj)[0]
    if (hit) {
        return hit.copy
    }

    const copy = Array.isArray(obj) ? [] : {}
    // 将copy首先放入cache, 因为我们需要在递归deepCopy的时候引用它
    cache.push({
        original: obj,
        copy
    })
    Object.keys(obj).forEach(key => {
        copy[key] = this.deepClone(obj[key], cache)
    })

    return copy
}

/**
 * 交换数组中元素的位置
 * @param arr
 * @param i
 * @param j
 */
common.swap = function (arr, i, j) {
    const temp = arr[i]
    arr[i] = arr[j]
    arr[j] = temp
}

/**
 * 获取到随机整数
 * @param min 最小值
 * @param max 最大值
 * @returns {number}
 */
common.getRandomInt = function (min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    //含最大值，含最小值
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

/**
 * 打开新页面
 * @param url
 */
common.openWindow = function (url) {
    let a = document.createElement('a');
    a.setAttribute('href', url);
    a.setAttribute('target', '_blank');
    a.setAttribute('id', 'd2admin-link-temp');
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(document.getElementById('d2admin-link-temp'))
};

export default common