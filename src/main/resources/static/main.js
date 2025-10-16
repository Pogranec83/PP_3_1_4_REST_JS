const userFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },

    getAllUsers: async () => await fetch('/api/admin'),
    getUserByEmail: async () => await fetch(`/api/admin/email`),
    getUserById: async (id) => await fetch(`/api/admin/` + id),
    addUser: async (user) => await fetch('/api/admin',
        {method: "POST", headers: userFetch.head, body: JSON.stringify(user)}),
    updateUser: async (user) => await fetch(`/api/admin/`,
        {method: 'PUT', headers: userFetch.head, body: JSON.stringify(user)}),
    deleteUserByID: async (id) => await fetch(`/api/admin/` + id,
        {method: 'DELETE', headers: userFetch.head})

}


infoUser()
getUsers()

function getRoles(list) {
    let userRoles = [];
    for (let role of list) {
        if (role.name === "ROLE_ADMIN") {
            userRoles.push(" ADMIN");
        }
        if (role.name === "ROLE_USER") {
            userRoles.push(" USER");
        }

    }
    let stringRoles = userRoles.join("  ");
    return stringRoles;
}

function addRoles(role) {
    let roles = [];
    if (role === "ADMIN") {
        roles.push({id: 1});
    } else {
        roles.push({id: 2});
    }
    return roles;
}


function getUsers() {
    userFetch.getAllUsers().then(
        res => {
            res.json().then(
                users => {
                    users.forEach((user) => {
                        console.log(user)
                        let stringRoles = getRoles(user.roles);
                        document.querySelector('#tableUsers').insertAdjacentHTML('beforeend',
                            `<tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${stringRoles}</td>
                <td>
                <button type="submit" onclick="editUser(${user.id})"
                class="btn btn-primary" data-toggle="modal" data-target="#editUser">Edit</button>
                </td>
                <td>
                <button type="submit" onclick="deleteUser(${user.id})"
                class="btn btn-danger" data-toggle="modal" data-target="#deleteUser">Delete</button>
                </td>
                </tr>`);
                    })
                }
            )
        }
    )
}

function addUserData() {
    document.addEventListener('DOMContentLoaded', addUserData);
    let name = document.getElementById('addName').value;
    let surname = document.getElementById('addSurname').value;
    let age = document.getElementById('addAge').value;
    let email = document.getElementById('addEmail').value;
    let password = document.getElementById('addPassword').value;
    let roles = document.getElementById('addRoles').value;
    let user = {
        name: name,
        surname: surname,
        age: age,
        email: email,
        password: password,
        roles: addRoles(roles)
    };
    console.log(user)
    userFetch.addUser(user).then(() => {
        document.getElementById('addName').value = ``;
        document.getElementById('addSurname').value = ``;
        document.getElementById('addAge').value = ``;
        document.getElementById('addEmail').value = ``;
        document.getElementById('addPassword').value = ``;
        document.getElementById('addRoles').value = ``;
        document.getElementById('tableUsers').innerHTML = ``;
        getUsers();
    })
}


function editUser(id) {
    userFetch.getUserById(id)
        .then(res => {
            res.json().then(user => {
                $('#editId').val(user.id)
                $('#editName').val(user.name)
                $('#editSurname').val(user.surname)
                $('#editAge').val(user.age)
                $('#editEmail').val(user.email)
                $('#editPassword').val("")
                $('#editRoles').val(getRoles(user.roles))
            })
        })
}


function updateUser() {
    let id = document.getElementById('editId').value;
    let name = document.getElementById('editName').value;
    let surname = document.getElementById('editSurname').value;
    let age = document.getElementById('editAge').value;
    let email = document.getElementById('editEmail').value;
    let password = document.getElementById('editPassword').value;
    let roles = document.getElementById('editRoles').value;
    let user = {
        id: id,
        name: name,
        surname: surname,
        age: age,
        email: email,
        password: password,
        roles: addRoles(roles)
    };
    console.log(user)
    console.log(JSON.stringify(user))
    userFetch.updateUser(user).then(() => {
        document.getElementById('editId').value = ``;
        document.getElementById('editName').value = ``;
        document.getElementById('editSurname').value = ``;
        document.getElementById('editAge').value = ``;
        document.getElementById('editEmail').value = ``;
        document.getElementById('editPassword').value = ``;
        document.getElementById('editRoles').value = ``;
        document.getElementById('tableUsers').innerHTML = ``;
        getUsers();
        $('#editUser').modal('hide');

    })
}


function deleteUser(id) {
    userFetch.getUserById(id)
        .then(res => {
            res.json().then(user => {
                $('#deleteId').val(user.id)
                $('#deleteName').val(user.name)
                $('#deleteSurname').val(user.surname)
                $('#deleteAge').val(user.age)
                $('#deleteEmail').val(user.email)
                $('#deleteRoles').val(getRoles(user.roles))
            })
        })
}

function deleteUserById() {
    let id = document.getElementById('deleteId').value;
    userFetch.deleteUserByID(id).then(() => {
        document.getElementById('deleteId').value = ``;
        document.getElementById('deleteName').value = ``;
        document.getElementById('deleteSurname').value = ``;
        document.getElementById('deleteAge').value = ``;
        document.getElementById('deleteEmail').value = ``;
        document.getElementById('deleteRoles').value = ``;
        document.getElementById('tableUsers').innerHTML = ``;
        getUsers();
        $('#deleteUser').modal('hide');
    });
}


function infoUser() {
    userFetch.getUserByEmail()
        .then(res => res.json())
        .then(user => {
            let stringRoles = getRoles(user.roles);
            document.querySelector('#infoUser').innerHTML = `
                ${user.email} with roles: ${stringRoles}
            `;
            document.querySelector('#userInfoPanel').innerHTML = `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${stringRoles}</td>
                </tr>
            `;
        });
}
