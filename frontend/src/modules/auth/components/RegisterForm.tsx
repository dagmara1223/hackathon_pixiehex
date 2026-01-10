function RegisterFrom(){
    return <div>
        <form>
            <label htmlFor="name">Nazwa użytkownika: </label>
            <input type="text" id="name" name="name"></input><br></br>
            <label htmlFor="password1">Hasło: </label>
            <input type="text" id="password1"></input><br></br>
            <label htmlFor="password2">Powtórz hasło: </label>
            <input type="text" id="password2"></input><br></br>
            <button>Submit</button>
        </form>
    </div>;
}

export default RegisterFrom;