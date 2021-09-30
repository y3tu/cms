const searchList = {
    baidu: {
        title: '百度',
        url: 'https://www.baidu.com/s',
        key: 'wd',
        icon: require('@/assets/img/icon_baidu.png'),
    },
    zhihu: {
        title: '知乎',
        url: 'https://www.zhihu.com/search',
        key: 'q',
        icon: require('@/assets/img/zhihu.png'),
    },
    juejin: {
        title: '掘金',
        url: 'https://juejin.cn/search',
        key: 'query',
        icon: require('@/assets/img/juejin.svg'),
    },
    github: {
        title: 'Github',
        url: 'https://github.com/search',
        key: 'q',
        icon: require('@/assets/img/github.png'),
    },
    gitee: {
        title: 'Gitee',
        url: 'https://search.gitee.com/',
        key: 'q',
        icon: require('@/assets/img/gitee.png'),
    },
    bing: {
        title: '必应',
        url: 'https://cn.bing.com/search',
        key: 'q',
        icon: require('@/assets/img/icon_bing.png'),
    },
    google: {
        title: 'Google',
        url: 'https://www.google.com/search',
        key: 'q',
        icon: require('@/assets/img/icon_google.png'),
    }
}

export default {
    searchList
}