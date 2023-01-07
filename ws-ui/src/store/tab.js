// 使用技术：vuex
// 作用：控制左侧菜单栏的隐藏与展开
export default{
    state:{
        // 控制菜单栏的状态
        isCollapse:false
    },
    mutations:{
        // 反转菜单栏的状态
        collapseChange(state){
            state.isCollapse=!state.isCollapse
        }
    }
}