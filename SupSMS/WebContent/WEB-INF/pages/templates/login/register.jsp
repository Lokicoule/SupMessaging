<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                        <div id="register" class="animate form">
                            <form  name="registerForm" autocomplete="on" method="post"> 
                                <h1> Sign up </h1> 
                                <p> 
                                    <label for="username" class="uname" data-icon="u"> Username</label>
                                    <input id="username" name="username" required="required" type="text" value="<c:out value="${user_signUp.userName}"/>" placeholder="mysuperusername69" />
                                    <span class="erreur">${erreurs_signUp.erreurs['username']}</span>
                                </p>
                                 <p> 
                                    <label for="firstname" class="uname" data-icon="u"> Firstname</label>
                                    <input id="firstname" name="firstname" required="required" type="text" value="<c:out value="${user_signUp.firstname}"/>" placeholder="mysuperfirstname69" />
                                    <span class="erreur">${erreurs_signUp.erreurs['firstname']}</span>
                                </p>
                                <p> 
                                    <label for="lastname" class="uname" data-icon="u"> Lastname</label>
                                    <input id="lastname" name="lastname" required="required" type="text" value="<c:out value="${user_signUp.lastname}"/>" placeholder="mysuperlastname69" />
                                    <span class="erreur">${erreurs_signUp.erreurs['lastname']}</span>
                                </p>
                                <p> 
                                    <label for="email" class="youmail" data-icon="e" > Email</label>
                                    <input id="email" name="email" required="required" type="email" value="<c:out value="${user_signUp.userEmail}"/>" placeholder="mysupermail@mail.com"/>
                                    <span class="erreur">${erreurs_signUp.erreurs['email']}</span>
                                </p>
                                <p> 
                                    <label for="phone" class="youmail" data-icon="e" > Phone</label>
                                    <input id="phone" name="phone" required="required" type="text" value="<c:out value="${user_signUp.userPhone}"/>" placeholder="06666666"/>
                                    <span class="erreur">${erreurs_signUp.erreurs['phone']}</span>
                                </p>
                                 <p> 
                                    <label for="address" class="youmail" data-icon="e" > Address</label>
                                    <input id="address" name="address" required="required" type="text" value="<c:out value="${user_signUp.userAddress}"/>" placeholder="mysuperaddress"/>
                                    <span class="erreur">${erreurs_signUp.erreurs['address']}</span>
                                </p>
                                 <p> 
                                    <label for="postalcode" class="youmail" data-icon="e" > Postal Code</label>
                                    <input id="postalcode" name="postalcode" required="required" type="text" value="<c:out value="${user_signUp.postalCode}"/>" placeholder="mysupercodepostal:10000"/>
                                    <span class="erreur">${erreurs_signUp.erreurs['postalcode']}</span>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p">Your password </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                    <span class="erreur">${erreurs_signUp.erreurs['password']}</span>
                                </p>
                                <p> 
                                    <label for="password_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
                                    <input id="password_confirm" name="password_confirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p class="signin button"> 
									<input type="submit" name="nameForm" value="Sign up"/> 
								</p>
                                <p class="change_link">  
									Already a member ?
									<a href="#tologin" class="to_register"> Go and log in </a>
								</p>
								<c:out value="test" />
                            </form>
                        </div>
