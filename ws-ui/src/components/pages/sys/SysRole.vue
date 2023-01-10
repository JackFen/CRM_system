<template>
    <div>
        角色管理
    </div>
</template>
<script>
export default{
    name:'sysRole',
    data(){
        return{}
    },
    mounted(){
        this.$http.get('http://localhost:8086/ws/sys/sysUser/list').then((res)=>{
            console.log(res)
        })
    }
}
</script>