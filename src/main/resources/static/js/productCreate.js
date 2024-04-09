const recipeOpen = document.getElementById('create-recipe-open');
const modalContainer = document.getElementById('create-modal-container');
const create= document.getElementById('create-modal-close');
const close = document.getElementById('close-create-modal')

recipeOpen.addEventListener('click', () => {
    modalContainer.classList.add('show');
});

create.addEventListener('click', () => {
    modalContainer.classList.remove('show');
});

close.addEventListener('click', () => {
    modalContainer.classList.remove('show');
});
