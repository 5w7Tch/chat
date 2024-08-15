document.addEventListener('DOMContentLoaded', function() {
    var searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', function() {
        var query = this.value;

        if (query.length > 1) {
            fetch('/searchAccount?query=' + query , {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                dataType: 'json'
            })
                .then(function(response) {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(function(data) {
                    var suggestions = document.getElementById('suggestions');
                    suggestions.innerHTML = '';
                    Object.entries(data).forEach(([key, value]) => {
                        var div = document.createElement('div');
                        div.className = 'suggestion-item';
                        div.innerHTML = ""
                        div.innerHTML += "<img style=\"width: 70px; height: 70px\" src=\""+value+"\" alt=\":picture:\">";
                        div.innerHTML += key;
                        div.addEventListener('click', function() {
                            window.location.href = '/searchAccount?query=' + key;
                        });
                        suggestions.appendChild(div);
                    });
                })
                .catch(function(error) {
                    console.error('There was a problem with fetch operation:', error.message);
                });


                // .then(response => response.json())
                // .then(data => {
                //     var suggestions = document.getElementById('suggestions');
                //     suggestions.innerHTML = '';
                //     data.forEach(function(pair){
                //         var div = document.createElement('div');
                //         div.className = 'suggestion-item';
                //         div.innerHTML = ""
                //         div.innerHTML += "<img style=\"width: 70px; height: 70px\" src=\""+pair.value+"\" alt=\":picture:\">";
                //         div.innerHTML += pair.key;
                //         div.addEventListener('click', function() {
                //             //todo : change it to visit profile
                //             searchInput.value = pair.key;
                //             suggestions.innerHTML = '';
                //         });
                //         suggestions.appendChild(div);
                //     });
                // });
        } else {
            document.getElementById('suggestions').innerHTML = '';
        }
    });
});