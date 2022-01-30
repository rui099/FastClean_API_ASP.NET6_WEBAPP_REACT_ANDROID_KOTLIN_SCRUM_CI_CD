import React, { Component} from "react";
import './forgot_pass.css';
import ImgFundo from '../../images/img2.jpg';
import api from "../../services/api";
import { getUser} from "../../services/auth";
import {FaSave} from "react-icons/fa";

class ForgotPass extends Component{
  
  state = {
    Email: "",
    NewPassword: "",
    Confirm_Password: "",
    error: "",
    sucess: ""
  };

  handleForgotPass = async e => {
    e.preventDefault();

    const { Email, NewPassword, Confirm_Password} = this.state;
    
      try {
          if(NewPassword.trim() === Confirm_Password.trim()){  
            await api.post("/forgetPassword/" + Email, { Email , NewPassword, Confirm_Password });
            this.setState({ sucess: "A Password foi alterada com Sucesso!" });
            document.getElementById("sucesso").style.color = "green";
            setTimeout(function(){
              document.getElementById("sucesso").innerHTML = '';
            }, 2000);
            document.getElementById("forgotpassform").reset();
          } else{
            this.setState({ error: "A nova password não foi confirmada corretamente!" });
            document.getElementById("err").style.color = "red";
            setTimeout(function(){
              document.getElementById("err").innerHTML = '';
            }, 2000);
          }
      } catch (err) {
        this.setState({ error: "Erro ao alterar a password. Verique os dados preenchidos!" });
      }
  };

render() {
return (
    <div className="displayInfo"> 
       <image class="Img" src={ImgFundo}/>
    <form id="forgotpassform" className="formFP" onSubmit={this.handleForgotPass}> 
      <h2>Esqueci-me da Password!</h2><br/>
      <hr/><br/>
        <div className="input-groupFP">
            <label htmlFor="Email"><b>Email da Conta:</b></label>
            <input type="email" name="Email" 
            onChange={e => this.setState({ Email: e.target.value })}
           required/> 
        </div> 
        <div className="input-groupFP">
            <label htmlFor="NewPassword"><b>Nova Password:</b></label>
            <input type="password" name="NewPassword" minLength={8} pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" 
            onChange={e => this.setState({ NewPassword: e.target.value })}
            title="A Pass deve ter 8 caracteres no mínimo: 1 Maiúscula, 1 Minúscula, 1 Número... " required/> 
        </div>
        <div className="input-groupFP">
            <label htmlFor="Confirm_Password"><b>Confirmar Password:</b></label>
            <input type="password" name="Confirm_Password" minLength={8} pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"
             onChange={e => this.setState({ Confirm_Password: e.target.value })} 
             title="A Pass deve ter 8 caracteres no mínimo: 1 Maiúscula, 1 Minúscula, 1 Número... " required/> 
        </div>  
        <div>
          <div>
        {this.state.sucess && <p id="sucesso"><b>{this.state.sucess}</b></p>}
        {this.state.error && <p id="err"><b>{this.state.error}</b></p>} </div>
      <button className="primaryFP"> <FaSave/> Guardar</button></div>
    </form>
    </div>
);
}
}
export default ForgotPass;
