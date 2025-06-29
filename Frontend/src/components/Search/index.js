import React, { useEffect, useState } from "react";
import Loader from "react-loaders";
import "./index.scss";
import AnimatedLetters from "../AnimatedLetters";

const Search = () => {
    const [letterClass, setLetterClass] = useState('text-animate');
    const [name, setName] = useState('');
    const [nation, setNation] = useState('');
    const [position, setPosition] = useState('');
    const [team, setTeam] = useState('');
    const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
        const timer = setTimeout(() => {
            setLetterClass("text-animate-hover");
        }, 3000); 

        return () => { 
            clearTimeout(timer);
        }
    }, []);

    const handleSearchChange = event => {
        setSearchQuery(event.target.value);
    };

    const handleGoButtonClick = () => {
    const query = new URLSearchParams({
        name,
        nation,
        position,
        team
    }).toString();
    window.location.href = `/data?${query}`;
    };

    return (
        <>
            <div className="container teams-page">
                <h1 className="page-title">
                    <br/>
                    <br/>
                    <AnimatedLetters letterClass={letterClass} strArray={"Search".split("")} idx={15}/>
                </h1>
                <div className="search-bar">
                <input
                    type="text"
                    placeholder="Player Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Nation"
                    value={nation}
                    onChange={(e) => setNation(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Position"
                    value={position}
                    onChange={(e) => setPosition(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Team"
                    value={team}
                    onChange={(e) => setTeam(e.target.value)}
                />
                <button onClick={handleGoButtonClick}>Go</button>
            </div>
            </div>
            <Loader type="pacman"/>
        </>
    );
}

export default Search;
