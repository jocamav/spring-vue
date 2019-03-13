# Learning Vue.js with Spring Boot

## Compile and Run
To run the example locally, execute next command:

`mvn spring-boot:run`

Then, open in a browser:

`http://localhost:8080`

## Starting

You can start with the initial version of the project, checkout the initial tag.

`git checkout initial`

In index.html file you should find:

```html
<!-- development version, includes helpful console warnings -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<div id="app">
    {{ message }}
</div>

<script>
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue!'
        }
    })
</script>
```

## References
You can find some extra documentation here:

* [Getting started with Vue.js](https://vuejs.org/v2/guide/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

