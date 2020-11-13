<template>
    <div>
        <user-info
            :firstName="this.$store.state.user.firstName"
            :secondName="this.$store.state.user.secondName"
            :lastName="this.$store.state.user.lastName"
        ></user-info>
    </div>
</template>

<script>
import UserInfo from '@/components/shared/user/UserInfo'

export default {
    components: {
        UserInfo,
    },
    data(){
        return {
        }
    },
    beforeCreate(){
        // test
        this.$store.commit("setFirstName", "Danni");
        this.$store.commit("setSecondName", "Danilovich");
        this.$store.commit("setLastName", "Didro");
    },
    created(){
        console.log(this.$store.state.user);
    },
    

}
</script>