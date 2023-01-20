<template>
    <div v-loading="loading" style="height: 100%;width: 100%;">
        正在加载中...
    </div>
</template>

<script>


export default {
    name: "loginByGitee",
    data() {
        return {
            loading: true,
            type: "",
            callback:"",
            url:"",
        }
    },
    mounted() {
        this.loading = true;
        this.type = "GITEE";
        this.callback = this.$route.query.url;
        this.axios.post("/api/GITEE/callback",this.type,this.callback).then(({data}) =>{
            if (data.code == 200) {
                this.$store.commit("login", data.data);
                this.$store.commit("closeModel");
                this.$router.push({name:'/'})
            } else {
                this.$toast({ type: "error", message: data.msg });
            }
            this.loading = false;
        })

    }

}
</script>

<style scoped>

</style>

