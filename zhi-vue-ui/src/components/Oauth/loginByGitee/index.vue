<template>
    <div v-loading="loading" style="height: 100%;width: 100%;">


    </div>
</template>

<script>


export default {
    name: "loginByGitee",
    data() {
        return {
            loading: true,



        }
    },
    mounted() {
        //获取用户id
        this.userid = this.$route.query.userid;
        let param = new URLSearchParams();
        param.append("userid",  this.userid);
        this.axios.post("/api/oauth/login/",param).then(({data}) =>{
            if (data.code == 200) {
                this.username = "";
                this.password = "";
                this.$store.commit("login", data.data);
                this.$store.commit("closeModel");
                this.$router.push("/")
                this.$toast({ type: "success", message: "登录成功" });
            } else {
                this.$toast({ type: "error", message: data.msg });
            }
        })



    }

}
</script>

<style scoped>

</style>

