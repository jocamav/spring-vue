var API_TODO_URL = '/api/todo/';

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
		todos: [],
        editedTodo: null,
        visibility: 'all'
	},
    mounted : function() {
        axios
            .get(API_TODO_URL)
            .then(response => {
            	this.todos = response.data;
            })
            .catch(function(error) {
                console.log(error)
            });
	},
    computed: {
        filteredTodos: function () {
            return filters[this.visibility](this.todos);
        },
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
		},
        removeTodo: function (todo) {
            axios.delete(API_TODO_URL + todo.id)
            .then(response => {
                var index = this.todos.indexOf(todo);
                this.todos.splice(index, 1);
            })
            .catch(error => {
            	console.log(error);
            })
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
            else {
	            axios.post(API_TODO_URL+todo.id, todo)
	            .then(response => {
	                todo = response.data
	            })
	            .catch(error => {
	            	console.log(error);
	            });
            }
        },
        cancelEdit: function (todo) {
            this.editedTodo = null;
            todo.title = this.beforeEditCache;
        },
        removeCompleted: function () {
        	axios.post(API_TODO_URL+'deletecompleted')
            .then(response => {
                this.todos = filters.active(this.todos);
            })
            .catch(error => {
            	console.log(error);
            });
        },
        todoToggle: function (todo) {
        	axios.post(API_TODO_URL+todo.id, todo)
            .then(response => {
                todo = response.data
            })
            .catch(error => {
            	console.log(error);
            });
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

