import React, { Component} from "react";
import './home.css';
import ImgBV from '../../images/imgbv.jpg';
import {USER} from '../../services/auth';


function home(){
    const objeto = JSON.parse(localStorage.getItem(USER));
return (
    <div style={{backgroundColor: "lightblue", border: "8px double blue"}}>
    <div className="bv"> 
        
        <h2 style={{color: "black", fontFamily: "cursive", textAlign: "center"}}> Bem-Vindo(a) {objeto.nome} ! </h2> </div>
     <div >
     <image id="imgbv" src={ImgBV}/><h3 style={{textAlign: "center", color: "black"}}> FastClean sempre consigo! </h3> </div>
     </div>
);
}

export default home;