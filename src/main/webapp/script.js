function registerEditButton() {
    let button = document.getElementById("edit-profile-button");
    button.addEventListener("click", () => {
        let item = document.getElementById("firstName");
        let text = item.textContent;

        let input = document.createElement("input");
        input.type = "text";
        input.value = text;
        item.parentNode.replaceChild(input, item);
    });
}

registerEditButton();
