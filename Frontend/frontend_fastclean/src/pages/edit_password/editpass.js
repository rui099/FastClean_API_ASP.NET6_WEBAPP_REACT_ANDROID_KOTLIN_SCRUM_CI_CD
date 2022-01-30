import React, { Component} from "react";
import './editpass.css';
import ImgFundo from '../../images/img2.jpg';
import api from "../../services/api";
import { getUser} from "../../services/auth";

class Editpass extends Component{
  
  state = {
    OldPassword: "",
    Password: "",
    Confirm_Password: "",
    error: "",
    sucess: ""
  };

  handleEditPass = async e => {
    e.preventDefault();

    const { OldPassword, Password, Confirm_Password} = this.state;
    const user = JSON.parse(getUser());
    const Id = user.id;
      try {
          if(Password.trim() === Confirm_Password.trim()){  
            await api.post("/updatePassword/" + Id, { OldPassword ,Password, Confirm_Password });
            this.setState({ sucess: "A Password foi alterada com Sucesso!" });
            document.getElementById("sucesso").style.color = "green";
            setTimeout(function(){
              document.getElementById("sucesso").innerHTML = '';
            }, 2000);
            document.getElementById("editpassform").reset();
          } else{
            this.setState({ error: "A nova password não foi confirmada corretamente!" });
            document.getElementById("err").style.color = "red";
            setTimeout(function(){
              document.getElementById("err").innerHTML = '';
            }, 2000);
          }
      } catch (err) {
        this.setState({ error: "Erro ao editar password. Verique os dados preenchidos!" });
      }
  };

render() {
return (
    <div className="displayInfo"> 
       <image class="Img" src={ImgFundo}/>
    <form id="editpassform" className="form2" onSubmit={this.handleEditPass}> 
      <h2>Editar Password</h2><br/>
      <hr/><br/>
        <div className="input-group2">
            <label htmlFor="OldPassword"><b>Password Antiga:</b></label>
            <input type="password" name="OldPassword" 
            onChange={e => this.setState({ OldPassword: e.target.value })}
           required/> 
        </div> 
        <div className="input-group2">
            <label htmlFor="Password"><b>Password Nova:</b></label>
            <input type="password" name="Password" minLength={6} pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,}$" 
            onChange={e => this.setState({ Password: e.target.value })}
            title="A Pass deve ter 6 caracteres no mínimo: 1 Maiúscula, 1 Minúscula, 1 Número... " required/> 
        </div>
        <div className="input-group2">
            <label htmlFor="Confirm_Password"><b>Confirmar Password:</b></label>
            <input type="password" name="Confirm_Password" minLength={6} pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,}$"
             onChange={e => this.setState({ Confirm_Password: e.target.value })} 
             title="A Pass deve ter 6 caracteres no mínimo: 1 Maiúscula, 1 Minúscula, 1 Número... " required/> 
        </div>  
        <div>
          <div>
        {this.state.sucess && <p id="sucesso"><b>{this.state.sucess}</b></p>}
        {this.state.error && <p id="err"><b>{this.state.error}</b></p>} </div>
         </div>
      <button className="primary">Alterar</button>
    </form>
    </div>
);
}
}
export default Editpass;