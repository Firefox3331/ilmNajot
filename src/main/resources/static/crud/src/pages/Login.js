import { useNavigate } from 'react-router-dom';
import { apiCall } from '../utils/apiCall'
import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import './index.css'

function Login() {

    const navigate = useNavigate();

    function submit(e) {
        e.preventDefault();
        let data = {
            username: e.target[0].value,
            password: e.target[1].value
        }
        apiCall('/auth/sign-in', 'post', data, null).then(res=>{
            console.log(res.data);
            localStorage.setItem("token", res.data.token);
            if(localStorage.getItem("token") === res.data.token){
                navigate('/')
            }
        })
    }

    return (
        <div className="login">
            <div className='container'>
                <div className="row wrapper">
                    <div className="card col-5">
                        <form onSubmit={submit} action="">
                            <div className="card-header">
                                <h5 className='text-center'>Login</h5>
                            </div>
                            <div className="card-body">
                                <input type="text" className='form-control my-2' placeholder='Email...' />
                                <input type="text" className='form-control my-2' placeholder='Password...' />
                            </div>
                            <div className="card-footer">
                                <button className='btn btn-warning w-100'>submit</button>
                                <a href="http://localhost:3000/register">go to register</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;