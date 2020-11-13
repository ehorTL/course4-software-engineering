<template>
    <div>
        <div>{{firstName}}</div>
        <div>{{secondName}}</div>
        <div>{{lastName}}</div>
        <div>{{email}}</div>
    </div>
</template>

<script>
export default{
    name: 'usrinfo',
    props: {
        firstName: String,
        secondName: String,
        lastName: String,
        email: String
    },
    beforeCreate(){

    },
    data(){
        return {
            
        }
    }

}
</script>