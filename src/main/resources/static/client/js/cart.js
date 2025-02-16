
const limit = document.querySelector("#select-limit-item");

limit.addEventListener('change',() => {
    let url = new URL(window.location.href);
    const params = url.searchParams;
    let value = limit.value;
    console.log(params.toString())
    if(params.has('limit')){
        console.log('has');
        params.set('limit',value)
    }else{
        console.log('not has ');
        params.append('limit',value);
    }
    window.location.href = url.pathname + "?" + params.toString();
})
