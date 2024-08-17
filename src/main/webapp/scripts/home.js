
friendListListener();
function friendListListener() {
    const answerResponseDivs = document.querySelectorAll('.buttonF');
    console.log(answerResponseDivs.length);
    answerResponseDivs.forEach(div => {
        div.addEventListener('click', function() {
            console.log(3);
            window.location.href = "/chatWith?to="+this.getAttribute('name');
        });
    });
}

