import React, { Component} from "react";
import './login.css';
import ImgFundo from '../../images/img2.jpg';
import ImgFC from '../../images/fastclean.ico';
import api from "../../services/api";
import { login, setnome } from "../../services/auth";

class Login extends Component {
  state = {
    email: "",
    password: "",
    error: ""
  };

  handleSignIn = async e => {
    e.preventDefault();

    const { email, password } = this.state;
    if (!email || !password) {
      this.setState({ error: "Preencha e-mail e password para entrar!" });
    } else {
      try {
        const response = await api.post("/Login", { email, password });
        login(response.data.token);
        setnome(JSON.stringify(response.data)); 
        window.location.href = '/homePage';
      } catch (err) {
        this.setState({
          error:
            "Erro no Login. Verique os dados preenchidos!"
        });
      }
      
    }
  };

  render() {
    return (
      <div className="displayInfo"> 
      <image class="Img" src={ImgFundo}/>
      <form className="form" onSubmit={this.handleSignIn}>  
    <div>
    <image id="imgfc" src={ImgFC}/> <h2> Login </h2> </div>
      <div className="input-group">
        <label id="lbl1" htmlFor="email"><b>Email:</b></label>
        <input type="text" name="email" placeholder="jc@gmail.com" onChange={e => this.setState({ email: e.target.value })}/> 
      </div>
      <div className="input-group">
        <label htmlFor="password"><b>Password:</b></label>
        <input type="password" name="password" onChange={e => this.setState({ password: e.target.value })} />
      </div>
      <div>
      {this.state.error && <p>{this.state.error}</p>}<br/>
      </div>
      <a href="/forgotPass"> <b>Esqueci-me da Password!</b> </a><br/><br/>
      <button className="primary">Login</button>
    </form>
    </div>  
    );
  }
}
export default Login;