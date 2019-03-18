# Learning Vue.js with Spring Boot

## Compile and Run
To run the example locally, execute next command:

`mvn spring-boot:run`

Then, open in a browser:

`http://localhost:8080`

## Starting

You can start with the initial version of the project, checkout the initial tag.

`git checkout -b initial`

In `index.html` file you should find as starting point:

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

## Organize the code

Let's take the css files into `css` folder
* base.css
* index.css

Also, we can move the jss code into a single file called `app.js`.

```javascript
var app = new Vue({
	el : '#app-todo',
	data : {
		message : 'Hello Vue!'
	}
})
```

And modify the `index.html` to include the css files and the js file in the botton.

```html
<html>
	<head>
		<meta charset="utf-8">
		<title>Vue.js - TodoMVC - Spring Boot</title>
		<link rel="stylesheet" href="css/base.css">
		<link rel="stylesheet" href="css/index.css">
		<style> [v-cloak] { display: none; } </style>
	</head>
</html>

<body>
	<div id="app-todo">{{ message }}</div>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
	<script src="js/app.js"></script>
</body>
```

## Basics

### Vue instance

Let's create a header section with some title. Replace the current `app-todo` div for this one.

```html
<div id="app-todo">
	<section class="todoapp" v-cloak>
		<header class="header">
			<h1>{{header}}</h1>
		</header>
	</section>
</div>
```

And in `app.js` let's insert this code. 

```javascript
var app = new Vue({
	el : '#app-todo',
	data : {
		header : 'todo'
	}
})
```

> This directive will remain on the element until the associated Vue instance finishes compilation. Combined with CSS rules such as [v-cloak] { display: none }, this directive can be used to hide un-compiled mustache bindings until the Vue instance is ready.

#### Instance Lifecycle Hooks

For example, the created hook can be used to run code after an instance is created. So create a new method `created` as part of the application. 

```javascript
data : {
    ···
},
created : function() {
	// `this` points to the vm instance
	console.log('The title: ' + this.header)
}
```

#### Interpolations

We saw how to include some text using the moustache operator. 

```html
<h1>{{header}}</h1>
```

#### Conditional

We can use the directive v-if

```html
<p v-if="seen">You have something</p>
```

The property should be visible

```
seen: true
```

> This is not going to be used in the application. So don't leave this code for the final version. 


To see how the binding of properties works, let's add a footer with some links under the `section.

```html
<footer class="info">
	<p>Double-click to edit a todo</p>
	<p>Find code in <a v-bind:href="githubUrl">Github</a></p>
	<p>Original project <a v-bind:href="mvcUrl">TodoMVC</a></p>
</footer>
```

And t`he links in the js file.

```javascript
data : {
    ···
    githubUrl: 'https://github.com',
    mvcUrl: 'http://todomvc.com'
}
```

### Adding a form

We can add an input under the `header`. 

```html
<input class="new-todo" autofocus autocomplete="off" placeholder="What needs to be completed?" v-model="newTodo">
```

We need to link the model and the event to a property in Vue.

```javascript
data : {
    ···
    newTodo: ''
}

```

### List rendering

Add some mock data in JS,

```javascript
var MOCK_TODOS = [
	{"id":1,"title":"Learn JavaScript","completed":false},
	{"id":2,"title":"Learn Vue","completed":true},
	{"id":3,"title":"Build something awesome","completed":false}
];

···

data : {
	···
	todos: MOCK_TODOS
}
```

And include the list in HTML under the input just created (yes...inside the current section).

```html
<section class="main" v-show="todos.length">
	<input id="toggle-all" class="toggle-all" type="checkbox">
	<label for="toggle-all">Mark all as complete</label>
	<ul class="todo-list">
		<li class="todo" v-for="todo in todos" :key="todo.id" :class="{completed: todo.completed}">
			<div class="view">
				<input class="toggle" type="checkbox" v-model="todo.completed">
				<label>{{todo.title}}</label>
				<button class="destroy"></button>
			</div>
		</li>
	</ul>
</section>
```

>Generally speaking, v-if has higher toggle costs while v-show has higher initial render costs. So prefer v-show if you need to toggle something very often, and prefer v-if if the condition is unlikely to change at runtime

Pay atention `:class="{completed: todo.completed}"` add a class to the element if the condition is true.

### Binding events

Let's create the method to add a new event:

Add `@keyup.enter="addTodo"` to the `input` field.

```html
<input class="new-todo" autofocus autocomplete="off" placeholder="What needs to be completed?" v-model="newTodo" @keyup.enter="addTodo">
```

And we will define the function `addTodo' into `methods` property.

```javascript
data : {
    ···
},
created : function() {
    ···
},
methods: {
    addTodo: function() {
        var value = this.newTodo && this.newTodo.trim();
        if (!value) {
            return;
        }
        this.todos.push({ id: this.todos.length + 1, title: value, completed: false });
        this.newTodo = '';
    }
}
```

>@keyup is equivalent to v-on. It's known as a Shorthand.

Now we can add the remove functionality. First we create the method in the JS file.

```javascript
methods: {
    ···
    removeTodo: function (todo) {
        var index = this.todos.indexOf(todo);
        this.todos.splice(index, 1);
    }
}
```

And now we add `@click="removeTodo(todo)"` to the remove button of each todo. 

```html
<button class="destroy" @click="removeTodo(todo)"></button>
```

### Computed properties

First, we are going to create some JS functions outside the Vue instance (just to make easier the filtering of the todos).

```javascript
var MOCK_TODOS = [
    ···
];

var filters = {
    all: function (todos) {
        return todos;
    },
    active: function (todos) {
        return todos.filter(function (todo) {
            return !todo.completed;
        });
    },
    completed: function (todos) {
        return todos.filter(function (todo) {
            return todo.completed;
        });
    }
};

var app = new Vue({
    ···
});
```

Now, we are going to some new computed properties.

```javascript
methods: {
    ···
},
computed: {
    remaining: function () {
        return filters.active(this.todos).length;
    },
    allDone: {
        get: function () {
            return this.remaining === 0;
        },
        set: function (value) {
            this.todos.forEach(function (todo) {
                todo.completed = value;
            });
        }
    }
}
```

> Instead of a computed property, we can define the same function as a method instead. For the end result, the two approaches are indeed exactly the same. However, the difference is that computed properties are cached based on their reactive dependencies. A computed property will only re-evaluate when some of its reactive dependencies have changed. 

So we add `v-model="allDone"` to the in the toggle-all input.

```html
<input id="toggle-all" class="toggle-all" type="checkbox" v-model="allDone">
```

Also, we'll need to include a new method to pluralize the item(s).

```javascript
methods: {
    pluralize: function (word, count) {
        return word + (count === 1 ? '' : 's');
    }
    ···
}        
```

Now, we are going to add a footer below the list of todos with the remaining pending items.

```html
<footer class="footer" v-show="todos.length">
    <span class="todo-count">
        <strong v-text="remaining"></strong> {{pluralize('item', remaining)}} left
    </span>
</footer>
```

### Routing

To use the Routing module, we need to include a new dependency in the HTML.

```html
	<script src="https://rawgit.com/flatiron/director/master/build/director.min.js"></script>
```

Then, we need a new computed property to show the filtered todos. And also a new property 

```javascript
computed: {
    
    ···
    
    filteredTodos: function () {
        return filters[this.visibility](this.todos);
    }
    
    ···
    
}
```

Like we are going to use different list of todos, we need to change the html from `v-for="todo in todos"` to `v-for="todo in filteredTodos"` to use the new computed property.

```html
<li class="todo" v-for="todo in filteredTodos" :key="todo.id" :class="{completed: todo.completed}">
```

In the `footer` section (after the `span`), we are going to add the links to:
* All todos
* Active todos
* Complete todos

Include in the js file a new property:

```
data : {
    ···
    visibility: 'all'
}
```

And include the links.

```html
<ul class="filters">
    <li><a href="#/all" :class="{selected: visibility == 'all'}">All</a></li>
    <li><a href="#/active" :class="{selected: visibility == 'active'}">Active</a></li>
    <li><a href="#/completed" :class="{selected: visibility == 'completed'}">Completed</a></li>
</ul>

```

Finally, we are going to add the Router configuration at the bottom of the `app.js` file.

```javascript
var router = new Router();

['all', 'active', 'completed'].forEach(function (visibility) {
	router.on(visibility, function () {
		app.visibility = visibility;
	});
});

router.configure({
	notfound: function () {
		window.location.hash = '';
		app.visibility = 'all';
	}
});

router.init();
```

> For this example we are using a 3rd-Party Router (Director). For most Single Page Applications, it’s recommended to use the officially-supported vue-router library. 

### Creating directives

First, we are going to add some new methods to edit a todo and also a new property ``editedTodo`:

```javascript
data : {
    ···
    editedTodo: null
    ···
},
	···
methods: {
    ····
    editTodo: function (todo) {
        this.beforeEditCache = todo.title;
        this.editedTodo = todo;
    },
    doneEdit: function (todo) {
        if (!this.editedTodo) {
            return;
        }
        this.editedTodo = null;
        todo.title = todo.title.trim();
        if (!todo.title) {
            this.removeTodo(todo);
        }
    },
    cancelEdit: function (todo) {
        this.editedTodo = null;
        todo.title = this.beforeEditCache;
    }
}

```

Now, we are going to create a new custom directive `todo-focus`.

```javascript
computed: {
    ···
},
directives: {
    'todo-focus': function (el, binding) {
        if (binding.value) {
            el.focus();
        }
    }
}
```

No we need to change the `li` component to include a class `editing` if a todo is being edited.

```html
:class="{completed: todo.completed, editing: todo == editedTodo}"
```

```html
<li class="todo" v-for="todo in filteredTodos" :key="todo.id" :class="{completed: todo.completed, editing: todo == editedTodo}">
```

Also, we add to the label a new event `@dblclick="editTodo(todo)"` when is double click to edit the todo:
 
```html
<label @dblclick="editTodo(todo)">{{todo.title}}</label>
```

And finally, to include a new input inside the `li` component after the existing view `div`:

```html
<input class="edit" type="text" v-model="todo.title" v-todo-focus="todo == editedTodo" @blur="doneEdit(todo)" @keyup.enter="doneEdit(todo)" @keyup.esc="cancelEdit(todo)">
```

Pay attention to:
* We are using the new directive v-todo-focus
* Also, we are linking some events to the function doneEdit(todo)
* Link the cancel function to the Esc key

### Remove completed

To finish, let's include a function to remove all the completed todos.

```javascript
methods: {
    ···
    removeCompleted: function () {
        this.todos = filters.active(this.todos);
    }
}
```

And include the button as last element of the footer (after the router links).

```html
<button class="clear-completed" @click="removeCompleted" v-show="todos.length > remaining">
    Clear completed
</button>
```

### Summary

The `index.html` should looks like:

```html
<html>
    <head>
        <meta charset="utf-8">
        <title>Vue.js - TodoMVC - Spring Boot</title>
        <link rel="stylesheet" href="css/base.css">
        <link rel="stylesheet" href="css/index.css">
        <style> [v-cloak] { display: none; } </style>
    </head>
</html>

<body>
    <div id="app-todo">
        <section class="todoapp" v-cloak>
            <header class="header">
                <h1>{{header}}</h1>
            </header>
            <input class="new-todo" autofocus autocomplete="off" placeholder="What needs to be completed?" v-model="newTodo" @keyup.enter="addTodo">
            <section class="main" v-show="todos.length">
                <input id="toggle-all" class="toggle-all" type="checkbox" v-model="allDone">
                <label for="toggle-all">Mark all as complete</label>
                <ul class="todo-list">
                    <li class="todo" v-for="todo in filteredTodos" :key="todo.id" :class="{completed: todo.completed, editing: todo == editedTodo}">
                        <div class="view">
                            <input class="toggle" type="checkbox" v-model="todo.completed">
                            <label @dblclick="editTodo(todo)">{{todo.title}}</label>
                            <button class="destroy" @click="removeTodo(todo)"></button>
                        </div>
                        <input class="edit" type="text" v-model="todo.title" v-todo-focus="todo == editedTodo" @blur="doneEdit(todo)" @keyup.enter="doneEdit(todo)" @keyup.esc="cancelEdit(todo)">
                    </li>
                </ul>
                <footer class="footer" v-show="todos.length">
                    <span class="todo-count">
                        <strong v-text="remaining"></strong> {{pluralize('item', remaining)}} left
                    </span>
                    <ul class="filters">
                        <li><a href="#/all" :class="{selected: visibility == 'all'}">All</a></li>
                        <li><a href="#/active" :class="{selected: visibility == 'active'}">Active</a></li>
                        <li><a href="#/completed" :class="{selected: visibility == 'completed'}">Completed</a></li>
                    </ul>
                    <button class="clear-completed" @click="removeCompleted" v-show="todos.length > remaining">
                        Clear completed
                    </button>
                </footer>
            </section>
        </section>
        <footer class="info">
            <p>Double-click to edit a todo</p>
            <p>Find code in <a v-bind:href="githubUrl">Github</a></p>
            <p>Original project <a v-bind:href="mvcUrl">TodoMVC</a></p>
        </footer>
    </div>
    <script src="https://rawgit.com/flatiron/director/master/build/director.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="js/app.js"></script>
</body>

```

And the `app.js` file:

```javascript
var MOCK_TODOS = [
    {"id":1,"title":"Learn JavaScript","completed":false},
    {"id":2,"title":"Learn Vue","completed":true},
    {"id":3,"title":"Build something awesome","completed":false}
];

var filters = {
    all: function (todos) {
        return todos;
    },
    active: function (todos) {
        return todos.filter(function (todo) {
            return !todo.completed;
        });
    },
    completed: function (todos) {
        return todos.filter(function (todo) {
            return todo.completed;
        });
    }
};

var app = new Vue({
    el : '#app-todo',
    data : {
        header : 'todo',
        githubUrl: 'https://github.com',
        mvcUrl: 'http://todomvc.com',
        newTodo: '',
        todos: MOCK_TODOS,
        visibility: 'all',
        editedTodo: null
    },
    created : function() {
        // `this` points to the vm instance
        console.log('The title: ' + this.header)
    },
    methods: {
        pluralize: function (word, count) {
            return word + (count === 1 ? '' : 's');
        },
        addTodo: function() {
            var value = this.newTodo && this.newTodo.trim();
            if (!value) {
                return;
            }
            this.todos.push({ id: this.todos.length + 1, title: value, completed: false });
            this.newTodo = '';
        },
        removeTodo: function (todo) {
            var index = this.todos.indexOf(todo);
            this.todos.splice(index, 1);
        },
        editTodo: function (todo) {
            this.beforeEditCache = todo.title;
            this.editedTodo = todo;
        },
        doneEdit: function (todo) {
            if (!this.editedTodo) {
                return;
            }
            this.editedTodo = null;
            todo.title = todo.title.trim();
            if (!todo.title) {
                this.removeTodo(todo);
            }
        },
        cancelEdit: function (todo) {
            this.editedTodo = null;
            todo.title = this.beforeEditCache;
        },
        removeCompleted: function () {
            this.todos = filters.active(this.todos);
        }
    },
    computed: {
        remaining: function () {
            return filters.active(this.todos).length;
        },
        allDone: {
            get: function () {
                return this.remaining === 0;
            },
            set: function (value) {
                this.todos.forEach(function (todo) {
                    todo.completed = value;
                });
            }
        },
        filteredTodos: function () {
            return filters[this.visibility](this.todos);
        }
    },
    directives: {
        'todo-focus': function (el, binding) {
            if (binding.value) {
                el.focus();
            }
        }
    }
});

var router = new Router();

['all', 'active', 'completed'].forEach(function (visibility) {
    router.on(visibility, function () {
        app.visibility = visibility;
    });
});

router.configure({
    notfound: function () {
        window.location.hash = '';
        app.visibility = 'all';
    }
});

router.init();


```

## Using axios to consume APIs

We need to include `axios` a new dependency into the project.

```html
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
```

Let's see how to use axios to consume a REST api. We'll declare a global variable with the URL of the API.



First, we'll change the `created` hook to use `mounted` hook. And with axis, we'll do a REST request.

```javascript
created : function() {
    // `this` points to the vm instance
    console.log('The title: ' + this.header)
}
```

To

```javascript
mounted : function() {
	axios
        .get(API_TODO_URL)
        .then(response => {
        	this.todos = response.data;
        })
        .catch(error => {
            console.log(error)
        });
}
```

And also, we can delete all the references to the mock data and change it to an empty array.

```javascript
todos: [],
```

If we refresh the page, we'll get the list of todos from the Database via REST. Now, we need to change the other methods to consume the REST api.

To add a new todo:

```javascript
addTodo: function() {
    var value = this.newTodo && this.newTodo.trim();
    if (!value) {
        return;
    }
    axios.put(API_TODO_URL, {
    	title: value
    })
    .then(response => {
        this.todos.push(response.data);
    })
    .catch(error => {
    	console.log(error);
    })
    .finally(() => (this.newTodo = ''));
}
```

To delete a todo.

```javascript
removeTodo: function (todo) {
    axios.delete(API_TODO_URL + todo.id)
    .then(response => {
        var index = this.todos.indexOf(todo);
        this.todos.splice(index, 1);
    })
    .catch(error => {
    	console.log(error);
    })
}
```

To update a todo.

```javascript
doneEdit: function (todo) {
    if (!this.editedTodo) {
        return;
    }
    this.editedTodo = null;
    todo.title = todo.title.trim();
    if (!todo.title) {
        this.removeTodo(todo);
    }
    else {
        axios.post(API_TODO_URL+todo.id, todo)
        .then(response => {
            todo = response.data
        })
        .catch(error => {
        	console.log(error);
        });
    }
}
```

For toggle the completed flag, we need to create a new method.

```javascript
methods: {
    ···
    todoToggle: function (todo) {
        axios.post(API_TODO_URL+todo.id, todo)
            .then(response => {
            todo = response.data
        })
    .catch(error => {
            console.log(error);
    });
    }
}
```

And link the change event to the checkbox of each todo.

```html
<input class="toggle" type="checkbox" v-model="todo.completed" @change="todoToggle(todo)">
```

For delete completed todos:

```javascript
removeCompleted: function () {
	axios.post(API_TODO_URL+'deletecompleted')
    .then(response => {
        this.todos = filters.active(this.todos);
    })
    .catch(error => {
    	console.log(error);
    });
}
```

For toogle all, we include a new method:

```javascript
methods: {
    ···
    toggleAll: function() {
        var actionUrl;
        if(this.allDone) {
            actionUrl = "allcompleted";
        }
        else {
            actionUrl = "allnotcompleted";
        }
        axios.post(API_TODO_URL+actionUrl)
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            console.log(error);
        });
    }
}
```

And the proper event handler `@change="toggleAll"` to the checkbox:

```html
<label for="toggle-all" @change="toggleAll">Mark all as complete</label>
```

And all the features should be working now! For testing everything together.

`mvn spring-boot:run`

Then, open in a browser:

`http://localhost:8080`

## References
You can find some extra documentation here:

* [Getting started with Vue.js](https://vuejs.org/v2/guide/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [axios](https://github.com/axios/axios)


