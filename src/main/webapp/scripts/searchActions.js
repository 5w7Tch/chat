document.addEventListener('DOMContentLoaded', function() {
    var searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', function() {
        var query = this.value;
        if (query.length > 1) {
            fetch('<%=request.getContextPath()%>/searchAccount?query=' + query , {
                method: "POST"
            })
                .then(response => response.json())
                .then(data => {
                    var suggestions = document.getElementById('suggestions');
                    suggestions.innerHTML = '';

                    data.forEach(([username, link]) => {
                        var div = document.createElement('div');
                        div.className = 'suggestion-item';
                        div.innerHTML = ""
                        console.log(link);
                        div.innerHTML += "<img style=\"width: 70px; height: 70px\" src=\""+link+"\" alt=\":picture:\">";
                        div.innerHTML += username;
                        div.addEventListener('click', function() {
                            //todo : change it to visit profile
                            searchInput.value = username;
                            suggestions.innerHTML = '';
                        });
                        suggestions.appendChild(div);
                    });
                });
        } else {
            document.getElementById('suggestions').innerHTML = '';
        }
    });
});