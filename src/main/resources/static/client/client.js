import { Client } from '@stomp/stompjs';

let todoList = [];

const clearTodoList = () => {
    const ulEl = document.getElementById('todo-list');
    ulEl.innerHTML = '';
};

const renderTodoList = (liElList) => {
    const ulEl = document.getElementById('todo-list');

    liElList.forEach((ul) => {
        ulEl.append(ul);
    })
};

const makeTodoEntries = (todoList) => {
    return todoList.map((todo) => {
        const liEl = document.createElement('li');
        liEl.innerText = todo.content;
        liEl.setAttribute('data-todo-id', todo.id);
        if (todo.isComplete) {
            liEl.style.textDecorationLine = "line-through";
        }

        const toggleBtn = document.createElement('span');
        toggleBtn.innerText = ' [/]';
        toggleBtn.className = 'btn';
        toggleBtn.addEventListener('click', async (event) => {
            const id = Number(event.target.parentNode.getAttribute('data-todo-id'));
            const todo = todoList.find((todo) => todo.id === id);
            try {
                await fetch(`/todo/${id}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        ...todo,
                        isComplete: !todo.isComplete
                    })
                });
            } catch (e) {
                alert("Error: " + e);
            }
        });
        liEl.append(toggleBtn);

        const deleteBtn = document.createElement('span');
        deleteBtn.innerText = ' [x]';
        deleteBtn.className = 'btn';
        deleteBtn.addEventListener('click', async (event) => {
            const id = Number(event.target.parentNode.getAttribute('data-todo-id'));
            try {
                await fetch(`/todo/${id}`, {
                    method: "DELETE",
                });
            } catch (e) {
                alert("Error: " + e);
            }
        });
        liEl.append(deleteBtn);

        return liEl
    });
};

const fetchTodos = async () => {
    const response = await fetch('/todo');
    try {
        const jsonData = await response.json();
        return jsonData.payload;
    } catch (e) {
        alert("Error: " + e);
    }
};

const initControl = () => {
    const inputEl = document.getElementById('new-todo-content');
    const btnEl = document.getElementById('add-btn');
    btnEl.addEventListener('click', async () => {
        try {
            await fetch(`/todo`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    content: inputEl.value,
                    isComplete: false,
                })
            });
            inputEl.value = '';
        } catch (e) {
            alert("Error: " + e);
        }
    })
};

const fetchAndRender = async () => {
    todoList = await fetchTodos();
    renderTodoList(makeTodoEntries(todoList));
};

window.onload = async () => {
    initControl();

    fetchAndRender();

    const client = new Client({
        brokerURL: 'ws://localhost:8899/ws',
        onConnect: () => {
            client.subscribe('/topic/test', message =>
            console.log(`Test Message Received: ${message.body}`)
            );
            client.subscribe('/topic/todos', message => {
            console.log(`Domain Message Received: ${message.body}`);
            if (message.body === 'added' || message.body === 'updated' || message.body === 'deleted') {
                clearTodoList();
                fetchAndRender();
            }
            });
        },
    });
      
    client.activate();
};