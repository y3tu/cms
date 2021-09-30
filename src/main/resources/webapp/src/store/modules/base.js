import Data from '../../config/data'

const state = {
    searchList: Data.searchList,
}

const getters = {
    searchTypes(state) {
        return Object.keys(state.searchList)
    },
}

export default {
    state,
    getters
}