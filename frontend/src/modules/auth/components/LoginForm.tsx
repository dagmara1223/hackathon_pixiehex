function LoginFrom(){
    return <div>
        <form>
            <label htmlFor="name">Nazwa użytkownika: </label>
            <input type="text" id="name" name="name"></input><br></br>
            <label htmlFor="password">Hasło: </label>
            <input type="text" id="password"></input><br></br>
            <button>Submit</button>
        </form>
    </div>;
}

export default LoginFrom;