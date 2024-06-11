import React, { useEffect, useState } from 'react';
import {apiCall} from '../utils/apiCall'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AdminPage() {

    const [users, setUsers] = useState([]);
    const navigate = useNavigate();

    useEffect(()=>{
        getAllUsers();
    }, [])

    function getAllUsers(){
        apiCall("/api/user/all", "get").then(res=>{
            setUsers(res.data);
            console.log(res.data);
        }).catch(er=>{
            alert("sizga ushbu pagega kirish uchun ruxsat yo'q!")
            navigate('/login');
        })
    }   

    function addRole(e, userId, roleId){
        console.log("salom");
            apiCall("/api/user/add-role/"+userId+"/"+roleId, 'put').then(res=>{
                getAllUsers();
            }).catch(err=>{

            })
    }

    return (
        <div>
            <div>
                <div></div>
                <div className="main">
                    <table className='table'>
                        <thead>
                            <tr>
                                <th>t/r</th>
                                <th>firstName</th>
                                <th>lastName</th>
                                <th>username</th>
                                <th>email</th>
                                <th>phoneNumber</th>
                                <th>gender</th>
                                <th>school</th>
                                <th>roles</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                users.map(user=> <tr>
                                    <td>{user.id}</td>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    <td>{user.username}</td>
                                    <td>{user.email}</td>
                                    <td>{user.phoneNumber}</td>
                                    <td>{user.gender}</td>
                                    <td>{user.school}</td>
                                    <td className='roles'>
                                        {
                                            user.roles.map(role=> <div className='role-wrap'>
                                                <p>{role.name.slice(5)}</p>
                                                <label htmlFor="" onClick={(e)=>addRole(e, user.id, role.id)} className='switch'>
                                                    <input type="checkbox" checked={role.hasrole}   />
                                                    <span className='slider round'></span>
                                                </label>
                                            </div>)
                                        }
                                    </td>
                                </tr>)
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default AdminPage;